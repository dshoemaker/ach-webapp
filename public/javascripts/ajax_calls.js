/**
 * Created by Dan on 3/10/2016.
 */

/* Function to handle deletion from list */
function del(delUrl) {
    $.ajax( {
        url: delUrl,
        type: 'DELETE',
        success: function(results) {
            location.reload();
        }
    });
}

function viewCase(viewUrl) {
    $.ajax({
        url: viewUrl,
        type: 'GET',
        dataType: 'html',
        success: function(response) {
            $('.case-view-container').append(response);
        },
        error: function(response) {
            $('.case-view-container').append('<p>Failed to find resource</p>');
        }
    });
}