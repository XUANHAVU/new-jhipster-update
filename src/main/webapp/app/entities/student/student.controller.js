(function () {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['$rootScope', '$scope', 'Student', '$http', '$log', 'Lop', '$state', '$stateParams'];

    function StudentController($rootScope, $scope, Student, $http, $log, Lop, $state, $stateParams) {
        var vm = this;

        vm.uploadFile = uploadFile;

        function uploadFile() {
            // myFile referes to the file-model in the html code
            var file = $scope.myFile;
            var len = file.name.length;
            var result = file.name;
            result = result.substring(len - 5, len);
            console.log(result);
            if (result != '.xlsx') {
                alert('File không đúng định dạng: file_name.xlsx');
            }
            else if (file.size > 3145728) {
                alert('File phải nhỏ hơn (<=) 3MB');
            }
            else {
                var uploadUrl = "/api/students/fileUpload";
                var fd = new FormData();
                fd.append('file', file);
                $http.post(uploadUrl, fd, {
                    headers: {'Content-Type': undefined}
                }).success(function (response) {
                    console.log("success")
                })
                    .error(function (error) {
                        console.log("error")
                    })
                    .then(
                        function (result) {
                            alert('Upload Success');
                            $state.reload();
                        }, function (error) {
                            alert('Upload Error');
                        });

            }
        };

        // $scope.doUploadFile = function () {
        //     var file = $scope.uploadedFile;
        //     var url = '/api/students/uploadfile';
        //     var data = new FormData();
        //
        //     data.append('uploadfile', file);
        //     var config = {
        //         transformRequest: angular.identity,
        //         transformResponse: angular.identity,
        //         headers: {
        //             'Content-Type': undefined
        //         }
        //     }
        //
        //     $http.post(url, data, config)
        //         .then(function (response) {
        //             $scope.uploadResult = response.data;
        //         }, function (response) {
        //             $scope.uploadResult = response.data;
        //         });
        // };

        vm.search = search;
        vm.change = change;

        vm.search2 = search2;
        vm.change2 = change2;

        vm.students = [];

        vm.lops = [];

        loadAll();

        function loadAll() {
            Student.query(function (result) {
                vm.students = result;
            });
            Lop.query(function (result) {
                vm.lops = result;
            });
        }

        function search() {
            vm.isSaving = true;

            var data = {
                name: vm.searchText ? vm.searchText : null
            };

            $http.post('/api/students/search', data)
                .then(
                    function (response) {
                        vm.isSaving = false;
                        vm.students = response.data;
                        $log.info(response);
                    },
                    function (response) {
                        vm.isSaving = false;
                    }
                );

        }

        function change() {
            search();
        }

        function search2(x) {
            var data = {
                //name: vm.searchText2 ? vm.searchText2 : null
                name: x === null ? null : vm.searchText2
            };

            $http.post('/api/students/filter', data)
                .then(
                    function (response) {
                        vm.students = response.data;
                    },
                    function (response) {
                    }
                );
            vm.searchText2 = null;
        }

        function change2() {
            search2();
        }
    }
})();
