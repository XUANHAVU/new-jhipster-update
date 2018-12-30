(function() {
    'use strict';
    angular
        .module('xuanhaApp')
        .factory('Gv', Gv);

    Gv.$inject = ['$resource'];

    function Gv ($resource) {
        var resourceUrl =  'api/gvs/:id';

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
