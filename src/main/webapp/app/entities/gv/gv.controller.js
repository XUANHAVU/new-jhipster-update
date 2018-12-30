(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('GvController', GvController);

    GvController.$inject = ['Gv'];

    function GvController(Gv) {

        var vm = this;

        vm.gvs = [];

        loadAll();

        function loadAll() {
            Gv.query(function(result) {
                vm.gvs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
