$(function(){
  var page=+$('input[name=page]').val()+1;
  var first=1;
  var pre=page-1;
  var next=page+1;
  var max=$('.page-item').length;
  var lock=[first,pre,page,next,max];
  
  if(max>10){
if(page<4){
    $('.page-item').each(function(i){
      if(lock.indexOf(i+1)<0){
        $(this).hide();
      }
      if(i===3)
      $(this).after('......');
    });
  }
  if(page>3&&page<max-2){
    $('.page-item').each(function(i){
      if(lock.indexOf(i+1)<0){
        $(this).hide();
      }
      if(i===pre-2)
      $(this).after('......');
      if(i===next)
      $(this).after('......');
    });
  }
  if(page>max-3){
    $('.page-item').each(function(i){
      if(lock.indexOf(i+1)<0){
        $(this).hide();
      }
      if(i===pre-2)
      $(this).after('......');
    });
  }
  }
  
        
    
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
    $('#go').click(function(){
      $('input[name="page"]').val(+$('.page-jump-to').val()-1);
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