<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(function () {
        $("#articleList").jqGrid({
                url:"${pageContext.request.contextPath}/article/queryAllByPage",
                editurl:"${pageContext.request.contextPath}/article/edit",// 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
                datatype:"json",
                colNames:["ID","标题","内容","img","状态","创建时间","发布时间","作者","操作"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",align:"center",editable:true},
                    {name:"content",align:"center",editable:true},
                    {name:"img",align:"center",editable:true,edittype:"file",
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
                    },
                    {name:"createDate",align:"center",formatter:"date"},
                    {name:"publishDate",align:"center"},
                    {name:"guruId",align:"center"},
                    {name:'option',
                        formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;&nbsp;";
                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('"+rowObject.id+"')\">删除</button>";
                            return button;
                        }
                    }


                ],
                styleUI:"Bootstrap",
                autowidth:true,//自动列宽
                multiselect:true,//复选框显示
                viewrecords:true,//是否显示总行数
                height:"40%",
                pager:"#articlePager",
                page:1,
                rowNum:4,
                rowList:[2,4,6,8,10]

        }).jqGrid("navGrid","#articlePager",{add:true,edit:true,del:true,search:true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {//在编辑之前或者之后进行额外的操作
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    //$('#title', obj).attr("disabled", true);
                    //obj.find("#title").attr("readonly",true);
                    obj.find("#img").attr("disabled",true);
                    //obj.find("#description").attr("readonly",true);
                }

            },
            {closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bId = response.responseJSON.articleId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${pageContext.request.contextPath}/article/uploadArticle",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{articleId:bId},
                        // 指定上传的input框id
                        fileElementId:"img",
                        success:function (aaa) {
                            $("#articleList").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            }

            );
    });

</script>
<%--*******************************************************************************--%>
<script>
    // 点击添加文章时触发事件
    function showArticle() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id","");
        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAllList",
            datatype: "json",
            type: "post",
            success: function (data) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }
        });
        $("#myModal").modal("show");
    }
    // 点击修改时触发事件
    function update(id) {
        // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化的问题
        // $("#articleList").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
        var data = $("#articleList").jqGrid("getRowData",id);
        $("#id").val(data.id);
        $("#title").val(data.title);
        // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
        KindEditor.html("#editor_id",data.content)
        // 处理状态信息
        $("#status").val(data.status);
        var option = "";
        if(data.status=="展示"){
            option += "<option selected value=\"1\">展示</option>";
            option += "<option value=\"2\">冻结</option>";
        }else{
            option += "<option value=\"1\">展示</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);
        // 处理上师信息

        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAllList",
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                gurulist.forEach(function (guru) {
                    if (guru.id==data.guruId){
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option2);
            }
        });
        $("#myModal").modal("show");


    }
    // 文件添加及修改方法
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            type: "post",
            // ajaxFileUpload 不支持serialize() 格式化形式
            // 只支持{"id":1,XXX:XX}
            // 解决: 1. 手动封装  2. 更改ajaxFileUpload的源码

            // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "inputfile",
            success: function (data) {
                $("#articleList").trigger("reloadGrid");
                $("#myModal").modal("hide");
            }
        })

    }

    // 点击删除时触发事件
    function del(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/deleteById",
            type: "post",
            data: {
                "id": id
            },
            datatype: "json",
            success: function (data) {
                $("#articleList").trigger("reloadGrid");
            }
        })
    }
</script>
<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">文章列表</a></li>
<%--
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="showArticle()">添加文章</a></li>
--%>
        <li><a onclick="showArticle()">添加文章</a></li>
    </ul>

</div>

<table id="articleList"></table>
<div id="articlePager" style="height: 60px"></div>