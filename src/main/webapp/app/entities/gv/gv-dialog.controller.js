(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('GvDialogController', GvDialogController);

    GvDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gv'];

    function GvDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gv) {
        var vm = this;

        vm.gv = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gv.id !== null) {
                Gv.update(vm.gv, onSaveSuccess, onSaveError);
            } else {
                Gv.save(vm.gv, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('xuanhaApp:gvUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
