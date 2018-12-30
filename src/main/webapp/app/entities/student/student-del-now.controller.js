(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .controller('StudentDeleteNowController',StudentDeleteNowController);

    StudentDeleteNowController.$inject = ['$state', '$timeout'];

    function StudentDeleteNowController($state, $timeout) {
        loadAll();
        function loadAll() {
            $timeout( function(){
                $state.go('student');
            }, 100 );
        }
    }
})();
