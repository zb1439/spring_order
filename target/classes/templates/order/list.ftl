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
                                <th>Order ID</th>
                                <th>Name</th>
                                <th>Phone Number</th>
                                <th>Address</th>
                                <th>Order Amount</th>
                                <th>Order Status</th>
                                <th>Payment Status</th>
                                <th>Create Time</th>
                                <th colspan="2">Operations</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.orderStatusEnum().getMsg()}</td>
                                    <td>${orderDTO.payStatusEnum().getMsg()}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">Detail</a></td>
                                    <#if orderDTO.orderStatusEnum().getCode() != 2>
                                        <td><a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">Cancel</a></td>
                                    </#if>
                                </tr>
                            </#list>
                        </table>
                    </div>
                    <#-- pages -->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="/sell/seller/order/list?page=${currentPage-1}&size=${currentSize}">Prev</a></li>
                            <#else>
                                <li><a href="#">Prev</a></li>
                            </#if>
                            <#list 1..orderDTOPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${currentSize}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="/sell/seller/order/list?page=${currentPage+1}&size=${currentSize}">Next</a></li>
                            <#else>
                                <li><a href="#">Next</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

<#--    pop up window -->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        Reminder
                    </h4>
                </div>
                <div class="modal-body">
                    You got a new order!
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="javascript:document.getElementById('notice').pause()" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="location.reload()" class="btn btn-primary">View new order</button>
                </div>
            </div>

        </div>

    </div>

    <audio id="notice" loop="loop" muted="muted">
        <source src="/sell/mp3/song.mp3" type="audio/mpeg">
    </audio>

    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var websocket = null;
        if ("WebSocket" in window) {
            websocket = new WebSocket("ws://zbfan.natapp1.cc/sell/webSocket")
        } else {
            alert("Your browser does not support websocket")
        }

        websocket.onopen = function (event) {
            console.log("creating websocket connection...")
        }

        websocket.onclose = function (event) {
            console.log("closing websocket connection...")
        }

        websocket.onmessage = function (event) {
            console.log("received msg: " + event.data)
            $('#myModal').modal('show')
            document.getElementById('notice').play()
        }

        websocket.onerror = function () {
            alert("websocket error")
        }

        window.onbeforeunload = function () {  // 卸载窗口
            websocket.close()
        }

    </script>

    </body>
</html>