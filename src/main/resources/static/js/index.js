$(function(){
        
    $('.delete').click(function(){
      $.getJSON('/home/delete/'+$(this).attr('value'));
      $(this).parents('tr').remove();
    });
    $('.page-item').click(function(){
      $('input[name="page"]').val(+$(this).text()-1);
      $('#form1').submit();
    });
    $('#search').click(function(){
      $('input[name="page"]').val(0);
      $('#form1').submit();
    });
    $('#datepicker').datepicker({
      uiLibrary: 'bootstrap4',
       format: 'yyyy-mm-dd' 
    });
    $('#input-datalist').autoComplete({
      minLength:1,
      resolverSettings: {
     url: '/currency.json'
     }
    });

  });