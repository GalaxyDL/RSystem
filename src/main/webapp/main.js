//show_list 展示列表fadein
//clear_list 淡出列表
//request_result 请求R点坐标
//request_list 请求心电信号文件名
//show_view 请求心电信号

var index;

function show_list() {
    $("#inside_left_box").fadeIn();
}

function clear_list() {
    $("#inside_left_box").fadeOut();
    //remove_li();
}

function send_form() {
    var data = new FormData();
    data.append("testfile", document.getElementById("file_upload").files[0]);
    $.ajax({
        url: "/ecg",
        type: "POST",
        dataType: "JSON",
        data: data,
        contentType: false,
        processData: false,
        success: function (rst) {
            console.log("the state is " + rst.state);
        }
    });
}

var Rpoints = [];

function request_result(file_name) {
    alert(file_name);

    // Rpoints.push();
}

//request_list 请求心电信号文件名
function request_list() {
    $.ajax({
        url: 'list',
        type: 'GET',
        dataType: 'json',
        timeout: 1000,
        cache: false,
        beforeSend: LoadFunction, //加载执行方法
        error: errorFunction,  //错误执行方法
        success: succFunction //成功执行方法
    })

    function LoadFunction() {
        $("#add_li").html('加载中...');
    }

    function errorFunction() {
        alert("error");
    }

    function succFunction(tt) {
        var json = eval(tt); //数组
        var lists;
        console.log(json);

        for (var i = 0; i < json.length; i++) {
            lists = "<li>" + json[i] + "<li/>";
            document.getElementById("add_li").innerHTML += lists;
        }
        var obj_lis = document.getElementById("add_li").getElementsByTagName("li");
        for (var i = 0; i < obj_lis.length; i++) {
            obj_lis[i].onclick = function () {
                var file_name;
                file_name = this.innerHTML;
                index = file_name;
                showView();
            }
        }
    }


}

function showView() {

    //初始化echarts实例
    var myChart = echarts.init(document.getElementById('right_box'));
    var option = myChart.getOption();
    console.log("get my chart" + option.series[0].data);
    myChart.showLoading();     //数据加载完之前先显示一段简单的loading动画


    $.ajax({
        type: "GET",
        dataType: "JSON",
        url: "ecg?id=" + index,
        //data:{}; //post
        success: on_get_ecg_succeed
    });

    // console.log("after" + option.series[0].data);

    //使用制定的配置项和数据显示图表

}

function on_get_ecg_succeed(result) {
    // if (result.id == index) {
    var option = myChart.getOption();
    console.log("former");
    var signal = result.signal;
    var xAxis = [];
    console.log("later");
    for (var i = 0; i < signal.length; i++) {
        xAxis.push(i);
    }
    myChart.hideLoading();
    option.xAxis[0].data = xAxis;　　　　　　　　　　　//隐藏加载动画
    option.series[0].data = signal;
    myChart.setOption(option);
    $.ajax({
        type: "GET",
        dataType: "JSON",
        url: "r?id=" + index,//待写 标注点请求
        success: on_get_r_succeed
    });
    // } else {
    //     alert("系统提示");
    //     myChart.hideLoading();    　　　　　　　　　　　　//隐藏加载动画
    // }
}

function on_get_r_succeed(data) {
    var option = myChart.getOption();
    var positions = data.positions;
    var mean = data.mean;
    var interval = data.interval;
    for (var i = positions.length - 1; i >= 0; i--) {
        option.series[0].markPoint.data[i] = {
            xAxis: positions[i],
            yAxis: option.series[0].data[positions[i]]
        };
    }
    document.getElementById("mean").innerHTML = mean;
    document.getElementById("interval").innerHTML = interval;
    myChart.setOption(option);
}

function remove_li() {
    $("#add_li li").remove();
}

var startvalue = 0;
var endvalue = 10;
var flag = 0;

function circle() {
    var option = myChart.getOption();
    console.log(option.dataZoom[0].start);
    startvalue = option.dataZoom[0].start;
    endvalue = option.dataZoom[0].end;
    flag = 0;
    var id = setInterval(function () {
        if (endvalue == 100) {
            window.clearInterval(id);
            return;
        }
        if (flag == 1) {
            window.clearInterval(id);
            return;
        }
        startvalue = startvalue + 0.25;
        endvalue = endvalue + 0.25;
        option.dataZoom[0].start = startvalue;
        option.dataZoom[0].end = endvalue;
        // console.log(startvalue);
        // console.log(endvalue);
        myChart.setOption(option);
    }, 50);

}

function end() {
    flag = 1;
}

function send_r() {
    var myChart = echarts.init(document.getElementById('right_box'));
    var option = myChart.getOption();
    var json = {};
    json.insert = insert;
    json.delete = del;
    json = JSON.stringify(json);
    $.ajax({
        url: "/r?id=" + index,
        type: "POST",
        dataType: "JSON",
        data: json,
        contentType: false,
        processData: false,
        success: function (rst) {
            alert("state=" + rst.state);
            insert.splice(0, insert.length);
            del.splice(0, del.length);
        }

    });

}

request_list();
