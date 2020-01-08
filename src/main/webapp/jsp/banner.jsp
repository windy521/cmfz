<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#bannerList").jqGrid({
                url:"${pageContext.request.contextPath}/banner/queryAllByPage",
                editurl:"${pageContext.request.contextPath}/banner/edit",// 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                datatype:"json",
                colNames:["ID","标题","路径","状态","描述","创建时间"],
                colModel:[
                    {name:"id"},
                    {name:"title",editable:true},
                    {name:"url",editable:true,edittype:"file",
                        formatter:function (val,row,rowJson) {
                            return "<img style='width:100px;height:50px' src='${pageContext.request.contextPath}"+val+"' />"
                        }
                    },
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
                pager:"#bannerPager",
                page:1,
                rowNum:4,
                rowList:[2,4,6,8,10]

        }).jqGrid("navGrid","#bannerPager",{add:true,edit:true,del:true,search:true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {//在编辑之前或者之后进行额外的操作
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    //$('#title', obj).attr("disabled", true);
                    //obj.find("#title").attr("readonly",true);
                    obj.find("#url").attr("disabled",true);
                    //obj.find("#description").attr("readonly",true);
                }

            },
            {closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (aaa) {
                            $("#bannerList").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            }

            );
    });

</script>

<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图列表</a></li>
        <li role="presentation" ><a href="${pageContext.request.contextPath}/banner/exportBanner">导出轮播图信息</a></li>
        <li role="presentation" ><a href="${pageContext.request.contextPath}/banner/importBanner">导入轮播图信息</a></li>
        <li><a onclick="importBannerExcel()">导入轮播图Excel信息</a></li>
        <%--<li role="presentation"><a href="#profile" onclick="outBanner()" aria-controls="profile" role="tab" data-toggle="tab">导出轮播图信息</a></li>--%>
    </ul>

</div>

<table id="bannerList"></table>
<div id="bannerPager" style="height: 60px"></div>


<script>
    // 点击导入BannerExcel时触发事件
    function importBannerExcel() {
        $("#bannerModal").modal("show");
    }
    function bannerSub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/banner/importBanner",
            type: "post",
            data: {
            },
            datatype: "json",
            fileElementId: "bannerFile",
            success: function (data) {
                //$("#articleList").trigger("reloadGrid");
                $("#bannerModal").modal("hide");
            }
        })

    }
</script>