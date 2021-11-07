<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--        sidebar-->
    <#include "../common/nav.ftl">
    <#--        content-->
    <div id="content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" name="categoryName" value="${(category.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Type</label>
                            <input type="number" class="form-control" name="categoryType" value="${(category.categoryType)!''}"/>
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>