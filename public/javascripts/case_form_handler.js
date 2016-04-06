/**
 * Created by Dan on 3/6/2016.
 */

$(document).ready(function() {
    var payment_number = 0;

    /* Initially hide case details panel in form */
    $('.details-panel').hide();

    // Add Payment click handler
    $('.add_pay_btn').click(function() {
        payment_number++;
        addPayment(payment_number);
    });

    // Remove Payment click handler
    $('.dynamic_payments').on('click', '.del_pay_btn', function() {
        var $row = $(this).closest('.row');
        $row.remove();
    });

    // Create/Remove 'Other Gov Benefits' textarea as needed
    $('#other-benefits-checkbox').change(function() {
        toggleGovBenefits($(this));
    });

    // Create/Remove cashier's check fields depending on Recovery method selection
    $('#recovery_selection').change(function() {
        toggleCashierInfo($(this));
    });

    /* Remove extra payment fields that are empty on Submit. There must be at least 1 payment so do not
       remove default even if it is blank.
     */
    $('#new-case-form').submit(function () {
        $(this).find('.payment_clone :input').filter(function () {
            return !this.value;
        }).attr("disabled", "disabled");
        return true; // ensure form still submits
    });

    // reset selected case type on page refresh and cancel
    $('#type_selection').val("");
    $('#case-type-cancel, #modal-close').click(function() {
        $('#type_selection').val("");
    });

    // Make sure the details panel is displayed if form is returned with errors on submit
    selectSubtype();
});


/* FUNCTIONS */

/* function to slide details panel into view after case subtype chosen */
function selectSubtype() {
    var case_subtype = $('#subtype_selection :selected').val();
    if (case_subtype !== "") {
        $('.details-panel').slideDown('slow');
        $('.pay_date_field').datepicker();
    }
    else {
        $('.details-panel').slideUp('slow');
    }
}

/* function to dynamically add payment fields */
function addPayment(pay_index) {
    var $template = $('#payment_template'),
        $clone = $template
            .clone()
            .removeClass('hide')
            .removeAttr('id')
            .insertBefore($template);

    //update the name attributes
    $clone
        .find('[name="pay_date"]').attr('name', 'paymentList[' + pay_index + '].effectiveDate').end()
        .find('[name="pay_amt"]').attr('name', 'paymentList[' + pay_index + '].amount').end();

    $('.dynamic_payments').append($clone);
    $('.pay_date_field').datepicker();
}

/* function to Create/Remove comments for government benefits depending on checkbox value */
function toggleGovBenefits($checkbox) {
    if ($checkbox.is(":checked")) {
        $('.other-gov-benefits').append('<div class="form-group col-md-7" id="other-benefits-textarea">' +
            '<label class="control-label" for="govBenefits">Government Benefits</label>' +
            '<textarea name="govBenefits" class="form-control" placeholder="Describe other benefits..."></textarea>' +
            '</div>');
    }
    else {
        $('#other-benefits-textarea').remove();
    }
}

/* function to Create/Remove cashier info from form depending on recovery method */
function toggleCashierInfo($dropdown) {
    var recovery_method = $dropdown.val();
    var cashier_fields =
        '<div class="row cashiers-check-details" style="display:none">' +
        '<div class="col-md-2">' +
        '<div class="form-group" id="checkNumber_field">' +
        '<label class="control-label" for="checkNumber">Check #</label>' +
        '<input type="text" id="checkNumber" name="checkNumber" value="" class="form-control">' +
        '</div></div>' +
        '<div class="col-md-4">' +
        '<div class="form-group" id="mailedTo_field">' +
        '<label class="control-label" for="mailedTo">Mailed to</label>' +
        '<input type="text" id="mailedTo" name="mailedTo" value="" class="form-control">' +
        '</div></div></div>';

    if (recovery_method === "Cashiers Check Mailed") {
        $(cashier_fields).appendTo($('#cashier-info-position')).slideDown("normal");
    }
    else {
        $('.cashiers-check-details').slideUp("normal", function () {
            $('.cashiers-check-details').remove();
        });
    }
}
