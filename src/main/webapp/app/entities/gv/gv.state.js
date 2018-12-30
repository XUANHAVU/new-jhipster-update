(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gv', {
            parent: 'entity',
            url: '/gv',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gvs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gv/gvs.html',
                    controller: 'GvController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gv-detail', {
            parent: 'gv',
            url: '/gv/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gv'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gv/gv-detail.html',
                    controller: 'GvDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gv', function($stateParams, Gv) {
                    return Gv.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gv',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gv-detail.edit', {
            parent: 'gv-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gv/gv-dialog.html',
                    controller: 'GvDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gv', function(Gv) {
                            return Gv.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gv.new', {
            parent: 'gv',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gv/gv-dialog.html',
                    controller: 'GvDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gv', null, { reload: 'gv' });
                }, function() {
                    $state.go('gv');
                });
            }]
        })
        .state('gv.edit', {
            parent: 'gv',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gv/gv-dialog.html',
                    controller: 'GvDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gv', function(Gv) {
                            return Gv.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gv', null, { reload: 'gv' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gv.delete', {
            parent: 'gv',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gv/gv-delete-dialog.html',
                    controller: 'GvDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gv', function(Gv) {
                            return Gv.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gv', null, { reload: 'gv' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
