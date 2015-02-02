function LocationController($scope, $location) {
    if($location.$$absUrl.lastIndexOf('/contacts') > 0){
        $scope.activeURL = 'contacts';
        
    } 
    else if($location.$$absUrl.lastIndexOf('/dishes') > 0){
    	$scope.activeURL = 'dishes';
    }
    else{
        $scope.activeURL = 'home';
    }
}