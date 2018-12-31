(function() {
    'use strict';

    angular
        .module('xuanhaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lop', {
            parent: 'entity',
            url: '/lop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Lops'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lop/lops.html',
                    controller: 'LopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('lop-detail', {
            parent: 'lop',
            url: '/lop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Lop'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lop/lop-detail.html',
                    controller: 'LopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Lop', function($stateParams, Lop) {
                    return Lop.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lop-detail.edit', {
            parent: 'lop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lop/lop-dialog.html',
                    controller: 'LopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lop', function(Lop) {
                            return Lop.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lop.new', {
            parent: 'lop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lop/lop-dialog.html',
                    controller: 'LopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                soluong: null,
                                name_class: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lop', null, { reload: 'lop' });
                }, function() {
                    $state.go('lop');
                });
            }]
        })
        .state('lop.edit', {
            parent: 'lop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lop/lop-dialog.html',
                    controller: 'LopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lop', function(Lop) {
                            return Lop.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lop', null, { reload: 'lop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lop.delete', {
            parent: 'lop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lop/lop-delete-dialog.html',
                    controller: 'LopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lop', function(Lop) {
                            return Lop.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lop', null, { reload: 'lop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
