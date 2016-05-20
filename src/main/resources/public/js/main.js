$( document ).ready(function() {

    $('#actionToShowUserDetails').click(function ()
        {
            var showUserDisplay = $('#showUserDetails').css('display');

            if ( showUserDisplay == 'none') {
                $('#showUserDetails').show();
                $('#actionToShowUserDetails').html('<span class="glyphicon glyphicon-user"></span>Hide User Details');
            }
            else {
                $('#showUserDetails').hide();
                $('#actionToShowUserDetails').html('<span class="glyphicon glyphicon-user"></span>Show User Details');
            }
        }
    )
});