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
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>Product ID</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Item Price</th>
                                <th>Stock</th>
                                <th>Description</th>
                                <th>Category</th>
                                <th>Create Time</th>
                                <th>Update Time</th>
                                <th colspan="2">Operations</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list productInfoPage.content as productInfo>
                                <tr>
                                    <td>${productInfo.productId}</td>
                                    <td>${productInfo.productName}</td>
                                    <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                                    <td>${productInfo.productPrice}</td>
                                    <td>${productInfo.productStock}</td>
                                    <td>${productInfo.productDescription}</td>
                                    <td>${productInfo.categoryType}</td>
                                    <td>${productInfo.createTime}</td>
                                    <td>${productInfo.updateTime}</td>
                                    <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">Modify</a></td>
                                    <#if productInfo.productStatusEnum().getCode() == 0>
                                        <td><a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">Off Shelf</a></td>
                                    <#else>
                                        <td><a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">On Sale</a></td>
                                    </#if>
                                </tr>
                            </#list>
                        </table>
                    </div>
                    <#-- pages -->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="/sell/seller/product/list?page=${currentPage-1}&size=${currentSize}">Prev</a></li>
                            <#else>
                                <li><a href="#">Prev</a></li>
                            </#if>
                            <#list 1..productInfoPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${index}&size=${currentSize}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte productInfoPage.getTotalPages()>
                                <li class="disabled"><a href="/sell/seller/product/list?page=${currentPage+1}&size=${currentSize}">Next</a></li>
                            <#else>
                                <li><a href="#">Next</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>