(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('LopController', LopController);

    LopController.$inject = ['Lop'];

    function LopController(Lop) {

        var vm = this;

        vm.lops = [];

        loadAll();

        function loadAll() {
            Lop.query(function(result) {
                vm.lops = result;
                vm.searchQuery = null;
            });
        }
    }
})();
