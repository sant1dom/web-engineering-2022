$(document).ready(function () {
    const selectpicker = $('.selectpicker');
    const formato = $('#formato');
    selectpicker.selectpicker();

    if (formato.val() === 'DIGITALE') {
        disableStatoCoservazione();
    }

    formato.on('change', function () {
        disableStatoCoservazione();
    });

    function disableStatoCoservazione() {
        let selected = $('#formato option:selected');
        let statoConservazione = $('#statoConservazione');
        if (selected.val() === 'DIGITALE') {
            statoConservazione.val('').trigger('change');
            statoConservazione.prop('disabled', true);
            statoConservazione.prop('required', false);
            statoConservazione.addClass('off');

        } else {
            statoConservazione.prop('disabled', false);
            statoConservazione.prop('required', true);
            statoConservazione.removeClass('off');
        }
        selectpicker.selectpicker('refresh');
    }

    $('#padre').on('change', function () {

        let titolo = $('#titolo');
        let anno = $('#anno');
        let etichetta = $('#etichetta');
        let genere = $('#genere');
        let autore = $('#autore');
        let tracce = $('#tracce');
        let msg = $('.warning-msg');

        let selected = $('#padre option:selected');

        if (selected.val().length > 0) {
            titolo.val(selected.text().split(' | ')[0])
            titolo.addClass('selected');
            titolo.prop('readonly', true);

            anno.val(selected.text().split(' | ')[1])
            anno.addClass('selected');
            anno.prop('readonly', true);

            etichetta.val(selected.text().split(' | ')[2])
            etichetta.addClass('selected');
            etichetta.prop('readonly', true);

            genere.val(selected.text().split(' | ')[3]).trigger('change');
            genere.addClass('selected');
            genere.prop('disabled', true);

            tracce.val("").trigger('change');
            tracce.prop('disabled', true);
            tracce.addClass('selected');
            tracce.prop('required', false);

            autore.val("").trigger('change');
            autore.prop('disabled', true);
            autore.addClass('selected');
            autore.prop('required', false);

            msg.text('Le informazioni in verde vengono ottenute dal disco selezionato.');
        } else {
            titolo.val("")
            titolo.removeClass('selected');
            titolo.prop('readonly', false);

            anno.val("")
            anno.removeClass('selected');
            anno.prop('readonly', false);

            etichetta.val("")
            etichetta.removeClass('selected');
            etichetta.prop('readonly', false);

            genere.val("").trigger('change');
            genere.removeClass('selected');
            genere.prop('disabled', false);

            tracce.prop('disabled', false);
            tracce.removeClass('selected');
            tracce.prop('required', true);

            autore.prop('disabled', false);
            autore.removeClass('selected');
            autore.prop('required', true);

            msg.text('');
        }
        selectpicker.selectpicker('refresh');
    });

    const add_tracce = $('#add-tracce');
    const add_autori = $('#add-autori');
    let isTracceHidden = true;
    let isAutoriHidden = true;

    add_tracce.hide()
    add_autori.hide()

    $('#add-tracce-btn').on('click', function () {
        if (isTracceHidden) {
            add_tracce.show()
            isTracceHidden = false;
        } else {
            add_tracce.hide()
            isTracceHidden = true;
        }
    })

    $('#add-autori-btn').on('click', function () {
        if (isAutoriHidden) {
            add_autori.show()
            isAutoriHidden = false;
        } else {
            add_autori.hide()
            isAutoriHidden = true;
        }
    })
});