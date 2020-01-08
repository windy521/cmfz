<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#albumList").jqGrid({
                url:"${pageContext.request.contextPath}/album/queryAllByPage",
                editurl:"${pageContext.request.contextPath}/album/edit",// 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                datatype:"json",
                colNames:["ID","标题","封面","分数","作者","播音","集数","状态","描述","发行时间"],
                colModel:[
                    {name:"id"},
                    {name:"title",editable:true},
                    {name:"cover",editable:true,edittype:"file",
                        formatter:function (val,row,rowJson) {
                            return "<img style='width:100px;height:50px' src='${pageContext.request.contextPath}"+val+"' />"
                        }
                    },
                    {name:"score",editable:true},
                    {name:"author",editable:true},
                    {name:"broadcast",editable:true},
                    {name:"count",editable:true},
                    {name:"status",editable:true,edittype:'select',
                        editoptions: {value:'1:展示;0:冻结'},
                        formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        }
                    },
                    //{name:"createDate",formatter:"date"},
                    {name:"description",editable:true},
                    {name:"createDate"}
                ],
                styleUI:"Bootstrap",
                autowidth:true,//自动列宽
                multiselect:true,//复选框显示
                viewrecords:true,//是否显示总行数
                height:"40%",
                pager:"#albumPager",
                page:1,
                rowNum:4,
                rowList:[2,4,6,8,10],
                //子页面
                subGrid : true,
                subGridRowExpanded:function (subgrid_id, albumId) {
                    addSubGrid(subgrid_id,albumId);
                }

        }).jqGrid("navGrid","#albumPager",{add:true,edit:true,del:true,search:true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {//在编辑之前或者之后进行额外的操作
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    //$('#title', obj).attr("disabled", true);
                    //obj.find("#title").attr("readonly",true);
                    obj.find("#cover").attr("disabled",true);
                    //obj.find("#description").attr("readonly",true);
                }

            },
            {closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${pageContext.request.contextPath}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{albumId:bId},
                        // 指定上传的input框id
                        fileElementId:"cover",
                        success:function (aaa) {
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            }

            );
    });

    function addSubGrid(subgrid_id,albumId) {
        var tableId=subgrid_id+"table";
        var divId=subgrid_id+"div";
        $("#"+subgrid_id).html(
            "<table id='"+tableId+"' ></table>"+"<div id='"+divId+"' ></div>"
        );
        $("#"+tableId).jqGrid({
            url:"${pageContext.request.contextPath}/chapter/queryAllByPage?albumId="+albumId,
            editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+albumId,
            datatype:"json",
            colNames:["id","标题","大小","时长","创建时间","操作"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"size"},
                {name:"time"},
                {name:"createTime",formatter:"date"},
                //{name:"url",editable:true,edittype:"file"},
                {name : "url",formatter:function (cellvalue, options, rowObject) {
                        var button = "<button type=\"button\" class=\"btn btn-success\" onclick=\"onPlay('"+cellvalue+"')\">播放</button>&nbsp;&nbsp;";
                        //                                                                声明一个onPlay方法 --> 显示模态框 ---> 为audio标签添加src  需要url路径作为参数传递
                        //                                                              'onPlay(参数)' ---> \"onPlay('"+cellvalue+"')\"
                        button+= "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>";
                        return button;
                    },editable:false,edittype:"file",editoptions:{enctype:"multipart/form-data"}}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#"+divId,
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[1,2,4,6],
            viewrecords:true
        }).jqGrid("navGrid","#"+divId,
            {},
            {closeAfterEdit:true},
            {
                closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        //url:"${pageContext.request.contextPath}/chapter/uploadChapter?albumId="+albumId,
                        url:"${pageContext.request.contextPath}/chapter/uploadChapter",
                        datatype:"json",
                        // 指定上传的input框id
                        fileElementId:"url",
                        data:{chapterId:chapterId},
                        success:function (data) {
                            $("#"+tableId).trigger("reloadGrid");
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                afterSubmit:function () {
                    $("#"+tableId).trigger("reloadGrid");
                    $("#albumList").trigger("reloadGrid");
                    return "adf";
                }
            }
        )

    }

    function onPlay(cellValue) {
        $("#music").attr("src","${pageContext.request.contextPath}"+ cellValue);
        $("#myModal").modal("show");
    }
    function download(cellValue) {
        location.href = "${pageContext.request.contextPath}/chapter/downloadChapter?name="+cellValue;
    }

</script>

<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">专辑列表</a></li>
        <%--<li role="presentation"><a href="#profile" onclick="outAlbum()" aria-controls="profile" role="tab" data-toggle="tab">导出轮播图信息</a></li>--%>
    </ul>

</div>

<table id="albumList"></table>
<div id="albumPager" style="height: 60px"></div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>