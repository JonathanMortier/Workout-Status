$( document ).ready(function() {

    $('#actionToShowUserDetails').click(function () {

        var selectorShowUserDetails = $('#showUserDetails');

        var showUserDisplay = selectorShowUserDetails.css('display');

        if ( showUserDisplay == 'none') {
            selectorShowUserDetails.show();
            $('#actionToShowUserDetails').html('<span class="glyphicon glyphicon-user"></span>Hide User Details');
        }
        else {
            selectorShowUserDetails.hide();
            $('#actionToShowUserDetails').html('<span class="glyphicon glyphicon-user"></span>Show User Details');
        }
    });

    /*$('#showButtons').click(function () {
        $('#listButtons').show();
        this.hide();
    });

    $('#validate').click(function () {
        $('#showButtons').show();
        this.parent.hide();
    });*/

});

function showButtons(id) {
    $('#updateButton'+id).hide();
    $('#listButtons'+id).show();
}

function hideButtons(id) {
    $('#lock'+id).parent().hide();
    $('#updateButton'+id).show();
}