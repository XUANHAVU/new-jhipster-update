(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('LopDeleteController',LopDeleteController);

    LopDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lop'];

    function LopDeleteController($uibModalInstance, entity, Lop) {
        var vm = this;

        vm.lop = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Lop.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
