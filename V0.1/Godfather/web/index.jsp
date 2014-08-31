<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Godfather</title>
    </head>
    <body>
        <header><div id="website_logo"></div><h1 id="website_name">GodFather</h1></header>
        <main>
            
            Upload file<br>
            <form action="UploadServlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="function" value="upload" />
                <input class="select_file" type="file" name="file" size="50" /><br>
                <select name="language_selection" class="language_selection">
                    <option value="java">Java</option>
                    <option value="php">PHP</option>
                    <option value="py">Python</option>
                </select>
                <input class="upload_file" type="submit" value="The Awesome Button" />
            </form>
            <br><br>
            <h3>Input Pseudo Code</h3>
            <form action="UploadServlet" method="post">
                <input type="hidden" name="function" value="input" />
                <textarea name="plain_text"><% if(request.getAttribute("pseudo")!= null) out.print(request.getAttribute("pseudo")); %></textarea>
                 <select name="language_selection" class="language_selection">
                    <option value="java">Java</option>
                    <option value="php">PHP</option>
                    <option value="py">Python</option>
                </select>
                <input class="upload_file" type="submit" value="The Awesome Button" />
            </form>
            
            <% if(request.getAttribute("code") != null){ %>
            
            <br><h3>Output</h3><br> <% out.print(request.getAttribute("code")); %>
            
            <% } %>
            
        </main>
    </body>
</html>
