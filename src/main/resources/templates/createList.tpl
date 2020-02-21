<!DOCTYPE html>
<html>
    <head>
        <title>Add New TopTenList</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

        <h1>Create New TopTen List</h1>

        <h3>${message}</h3>

        <form action="/topten/list?cmd=create" method="post">

            Title: <input type="text" name="title" size=60></input><br />
            <br />
            Item 10: <input type="text" name="item10" size=40></input><br />
            Item 9: <input type="text" name="item9" size=40></input><br />
            Item 8: <input type="text" name="item8" size=40></input><br />
            Item 7: <input type="text" name="item7" size=40></input><br />
            Item 6: <input type="text" name="item6" size=40></input><br />
            Item 5: <input type="text" name="item5" size=40></input><br />
            Item 4: <input type="text" name="item4" size=40></input><br />
            Item 3: <input type="text" name="item3" size=40></input><br />
            Item 2: <input type="text" name="item2" size=40></input><br />
            Item 1: <input type="text" name="item1" size=40></input><br />

            <input type="submit" value="Submit" />    
        </form>
        <form action="/topten/list" method="get">
            <input type="submit" value="Cancel" 
            name="Cancel" />
        </form>
    </body>
</html>
