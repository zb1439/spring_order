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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" id="productName" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Price</label>
                            <input type="text" class="form-control" id="productPrice" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Stock</label>
                            <input type="number" class="form-control" id="productStock" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Description</label>
                            <input type="text" class="form-control" id="productDescription" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Icon</label>
                            <img height="100px" width="100px" src="${(productInfo.productIcon)!''}" alt="icon from the following path">
                            <input type="text" class="form-control" id="productIcon" value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Category</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                        selected
                                        </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>