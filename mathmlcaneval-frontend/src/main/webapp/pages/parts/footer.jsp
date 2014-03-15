<div class="container">    
    <hr>
    <footer>
        <p>&copy; MIR&#64;MU 2013-2014</p>
    </footer>
</div>



<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/jquery-fileupload/vendor/jquery.ui.widget.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/jquery-fileupload/jquery.iframe-transport.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/jquery-fileupload/jquery.fileupload.js" />"></script><script type="text/javascript" src="<c:url value="/resources/js/shCore.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/shAutoloader.js" />"></script>
<script type="text/javascript"
        src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
</script>
<script>
    SyntaxHighlighter.autoloader(
            'xml  <c:url value="/resources/js/shBrushXml.js" />'
            );

    SyntaxHighlighter.all();

    $(document).ready(function() {
        if($("#userRolesRows > div").length === 0)
        {
            $("#userRolesRows").append(getUserRolesHTML()); 
        }  

        $(this).on('click','.addrow',function() {
            $("#userRolesRows").append(getUserRolesHTML()); 
        });
    });

    function getUserRolesHTML()
    {
        var count = $("#userRolesRows > div").length;
        console.log(count);
        var $html = $(".userRoleTemplate").clone();
        $html.find("select").attr({
            'id': 'userRoleForms' + count,
            'name' : 'userRoleForms['+count+']'
        });
        
        return $html.html();
    }
</script>
<script>
function refreshTable()
{
    $("#uploads-table").empty();
    for (var i = 0; i < $("#fileinput").prop('files').length; ++i) {
        $("#uploads-table").append('<tr><td>' + $("#fileinput").prop('files')[i].name + '</td><td>' + $("#fileinput").prop('files')[i].size + '</td></tr>');
    }
};
$(document).ready(function() {
    $("#fileinput").change(function() { refreshTable(); });
    $("#filereset").click(function(e) { 
        e.preventDefault();
        $(this).parents("form:first")[0].reset();
        refreshTable(); 
    });
});
</script>
<script>
$(document).ready(function() {
    $("#xml").on('load keyup keypress paste', function() {
        $("#mathml-preview").html($(this).val());
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathml-preview"]);
    }).trigger("keyup");
});
</script>
<script>
function showTooltip(data)
{
$('#btnCanon')
    .tooltip({
        title: data,
        trigger: 'manual',
        placement: 'top'
    }).tooltip('show');
    setTimeout(function() {
        $('#btnCanon').tooltip('hide');
        $('#myModal').modal('hide');
    }, 2000);
}
$(document).ready(function() { $("#btnCanon").click(function(){
 $.ajax({
     type : "Get",
     url : "${pageContext.request.contextPath}/formula/run/",   
     data : "id=${formulaEntry.id}",
     dataType: 'text',
     success : function(response) {  
         showTooltip("<spring:message code="entity.formula.started" />");
     }
  });
});
});
</script>
</body>
</html>
