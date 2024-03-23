$(function(){
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
          