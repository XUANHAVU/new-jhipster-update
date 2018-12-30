(function() {
    'use strict';
    angular
        .module('xuanhaApp')
        .factory('Lop', Lop);

    Lop.$inject = ['$resource'];

    function Lop ($resource) {
        var resourceUrl =  'api/lops/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
