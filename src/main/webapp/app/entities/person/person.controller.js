(function () {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('PersonController', PersonController);

    PersonController.$inject = ['Person', '$http'];

    function PersonController(Person, $http) {

        var vm = this;

        vm.people = [];

        vm.search = search;
        vm.change = change;

        loadAll();

        function loadAll() {
            Person.query(function (result) {
                vm.people = result;
            });
        }

        function search() {
            var data = {
                name: vm.searchText ? vm.searchText : null
            }
            $http.post('/api/people/search', data)
                .then(
                    function (response) {
                        vm.people = response.data;
                    },
                    function (response) {
                        console.log("Error HTTP POST Person Controller");
                    }
                );
        }

        function change() {
            search();
        }

    }
})();
