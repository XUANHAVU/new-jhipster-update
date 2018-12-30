(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('GvDeleteController',GvDeleteController);

    GvDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gv'];

    function GvDeleteController($uibModalInstance, entity, Gv) {
        var vm = this;

        vm.gv = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gv.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
