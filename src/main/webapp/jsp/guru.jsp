<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#guruList").jqGrid({
                url:"${pageContext.request.contextPath}/guru/queryAllByPage",
                editurl:"${pageContext.request.contextPath}/guru/edit",// 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                datatype:"json",
                colNames:["ID","姓名","法号","头像","状态"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"name",align:"center",editable:true},
                    {name:"nickName",align:"center",editable:true},
                    {name:"photo",align:"center",editable:true,edittype:"file",
                        formatter:function (val,row,rowJson) {
                            return "<img style='width:100px;height:50px' src='${pageContext.request.contextPath}"+val+"' />"
                        }
                    },
                    {name:"status",align:"center",editable:true,edittype:'select',
                        editoptions: {value:'1:展示;0:冻结'},
                        formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        }
                    }
                    //{name:"createDate",formatter:"date"},


                ],
                styleUI:"Bootstrap",
                autowidth:true,//自动列宽
                multiselect:true,//复选框显示
                viewrecords:true,//是否显示总行数
                height:"40%",
                pager:"#guruPager",
                page:1,
                rowNum:4,
                rowList:[2,4,6,8,10]

        }).jqGrid("navGrid","#guruPager",{add:true,edit:true,del:true,search:true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {//在编辑之前或者之后进行额外的操作
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    //$('#title', obj).attr("disabled", true);
                    //obj.find("#title").attr("readonly",true);
                    obj.find("#photo").attr("disabled",true);
                    //obj.find("#description").attr("readonly",true);
                }

            },
            {closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${pageContext.request.contextPath}/guru/uploadGuru",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{guruId:bId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (aaa) {
                            $("#guruList").trigger("reloadGrid");
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
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">上师列表</a></li>
        <%--<li role="presentation"><a href="#profile" onclick="outGuru()" aria-controls="profile" role="tab" data-toggle="tab">导出轮播图信息</a></li>--%>
    </ul>

</div>

<table id="guruList"></table>
<div id="guruPager" style="height: 60px"></div>