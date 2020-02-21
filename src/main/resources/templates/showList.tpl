<!DOCTYPE html>
<html>
    <head>
        <title>Top Ten Lists - Display List</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Top Ten List Viewer</h1>
        <h2>${topTenList.title}</h2>

        <ul>
            <#list topTenList.items as item>
                <li>${item}</li>
            </#list>
        </ul>

        <a href="/topten/list?cmd=showList&index=${prevIndex}">Previous List</a> &nbsp; &nbsp;
        <a href="/topten/list?cmd=showList&index=${nextIndex}">Next List</a><br/>
        <br />
        <a href="/topten/list?cmd=createList">Create New List</a>        
    </body>
</html>
