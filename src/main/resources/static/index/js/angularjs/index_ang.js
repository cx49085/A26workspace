/***********************AngularJs*******************************************/

var app = angular.module('index', []);
//倒计时
app.controller('log_reg', function ($scope, $interval, index_service) {

    //login
    $scope.login = function () {
        var param = {
            "phone": $scope.phone1,
            "password": $scope.password1
        };
        console.log(param);
        index_service.post("/user/login", param)
            .then(function successCallback(response) {
                // 请求成功执行代码
                console.log(response.data.info);
                alert(response.data.info)
            }, function errorCallback(response) {
                // 请求失败执行代码
                console.log(response.data.info);
                alert(response.data.info)
            });
        ;
    }
    //send yzm
    $scope.sendYzm = function () {
        var param = {
            "phone": $scope.phone2
        };
        //开始倒计时
        timePromise();
        //发送请求
        index_service.post("/user/getcode", param)
            .then(function successCallback(response) {
                // 请求成功执行代码
                console.log(response.data.info);
                alert(response.data.info)
            }, function errorCallback(response) {
                // 请求失败执行代码
                console.log(response.data.info);
                alert(response.data.info)
            });
    };
//register
    $scope.regsiter = function () {
        var param = {
            "phone": $scope.phone2,
            "password": $scope.password2,
            "nickname": $scope.nickname,
            "age": $scope.age,
            "gender": $scope.gender,
            "company": $scope.company,
            "work_type": $scope.work_type,
            "work_id": $scope.work_id,
            "code": $scope.yzm
        };
        console.log(param);
        //发送请求
        index_service.post("/user/register", param)
            .then(function successCallback(response) {
                // 请求成功执行代码
                console.log(response.data.info);
                alert(response.data.info)
            }, function errorCallback(response) {
                // 请求失败执行代码
                console.log(response.data.info);
                alert(response.data.info)
            });
    }


//返送验证码的倒计时
    $scope.times = "发送验证码"; //发送验证码
    function timePromise() { //时间显示
        var second = 6;
        $scope.btn_clicked = true;
        $interval(function () {
            if (second <= 0) {
                $interval.cancel(timePromise);
                $scope.btn_clicked = false;
                $scope.times = "重新发送";
            } else {
                $scope.times = second + "秒后可重发";
                second--;
            }
        }, 1000);
    }


})
;


app.service('index_service', function ($http, $interval) {


    this.post = function (commentFileUrl, param) {
        return $http({
            method: "POST",
            url: commentFileUrl,
            data: param,
            headers: {
                'Cache-Control': 'no-cache',
                'Content-Type': 'application/json'
            }
        });

    }

    this.get = function (commentFileUrl, param) {
        return $http({
            method: "get",
            url: commentFileUrl,
            data: param,
            headers: {
                'Cache-Control': 'no-cache',
                'Content-Type': 'application/json'
            }
        });
    }


})


// app.config(function($httpProvider) {
//     $httpProvider.defaults.useXDomain = true;
//     delete $httpProvider.defaults.headers
//         .common['X-Requested-With'];
// });
// app.factory('index_factory', function($interval) {
//     var backendUrl = "http://localhost:3000";
//     // var service = {
//     //         abc:
//     //     },

// return service;
// });