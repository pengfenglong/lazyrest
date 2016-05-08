--------------------------------------------------------------------------------------
您可以在你的程序中这样使用它们：
new SelectBuilder()
.column("name")
.column("age")
.from("Employee")
.where("dept = 'engineering'")
.where("salary > 100000")
.toString();
----------------------------------------------------------------------------------------

与Spring结合：
----------------------------------------------------------------------------------------
PreparedStatementCreator psc =
    new ParameterizedPreparedStatementCreator()
    .setSql("update Employee set name = :name where id = :id")
    .setParameter("name", "Bob")
    .setParameter("id", 42);

new JdbcTemplate(dataSource).update(psc)
----------------------------------------------------------------------------------------
PreparedStatementCreator psc =
    new UpdateCreator("Employee")
    .setValue("name", "Bob")
    .whereEquals("id", 42);

new JdbcTemplate(dataSource).update(psc);


--------------------------------------------------------------------------------------
DeleteBuilderTest:
	DeleteBuilder deleteBuilder=new DeleteBuilder("person");
	assertEquals("DELETE FROM person",deleteBuilder.toString());
	
	deleteBuilder.set("name = rollen");
	assertEquals("DELETE FROM person WHERE name = rollen",deleteBuilder.toString());
	
	deleteBuilder.set("age = 12");
	assertEquals("DELETE FROM person WHERE name = rollen AND age = 12",deleteBuilder.toString());
--------------------------------------------------------------------------------------	
	
--------------------------------------------------------------------------------------	
InsertBuilderTest:
	InsertBuilder builder;
    builder = new InsertBuilder("Person");
    assertEquals("INSERT INTO Person () VALUES ()",builder.toString());

    builder.set("name", "rollen");
    assertEquals("INSERT INTO Person (name) VALUES (rollen)",builder.toString());

    builder.set("age", "12");
    assertEquals("INSERT INTO Person (name, age) VALUES (rollen, 12)", builder.toString());
--------------------------------------------------------------------------------------   
    
    
--------------------------------------------------------------------------------------   
SelectBuilderTest
	SelectBuilder selectBuilder = new SelectBuilder("Employee");
    assertEquals("SELECT * FROM Employee", selectBuilder.toString());

    selectBuilder.column("userName as name");
    assertEquals("SELECT userName as name FROM Employee", selectBuilder.toString());

    selectBuilder.column("age", true);
    assertEquals("SELECT userName as name, age FROM Employee GROUP BY age", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee e").where("name like 'Bob%'");
    assertEquals("SELECT * FROM Employee e WHERE name like 'Bob%'", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee e").where("name like 'Bob%'").where("age > 37");
    assertEquals("SELECT * FROM Employee e WHERE name like 'Bob%' AND age > 37", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee e").join("Department d on e.dept_id = d.id");
    assertEquals("SELECT * FROM Employee e JOIN Department d on e.dept_id = d.id", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee e").join("Department d on e.dept_id = d.id").where("name like 'Bob%'");
    assertEquals("SELECT * FROM Employee e JOIN Department d on e.dept_id = d.id WHERE name like 'Bob%'", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee e").orderBy("name");
    assertEquals("SELECT * FROM Employee e ORDER BY name", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee e").orderBy("name desc").orderBy("age");
    assertEquals("SELECT * FROM Employee e ORDER BY name desc, age", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee").where("name like 'Bob%'").orderBy("age");
    assertEquals("SELECT * FROM Employee WHERE name like 'Bob%' ORDER BY age", selectBuilder.toString());

    selectBuilder = new SelectBuilder("Employee").where("id = 42").forUpdate();
    assertEquals("SELECT * FROM Employee WHERE id = 42 FOR UPDATE", selectBuilder.toString());
--------------------------------------------------------------------------------------   
    
    
--------------------------------------------------------------------------------------   
UpdateBuilderTest
	UpdateBuilder updateBuilder = new UpdateBuilder("Person");
    assertEquals("UPDATE Person", updateBuilder.toString());

    updateBuilder.set("name = rollen");
    assertEquals("UPDATE Person SET name = rollen", updateBuilder.toString());

    updateBuilder.set("age = 20");
    assertEquals("UPDATE Person SET name = rollen, age = 20", updateBuilder.toString());

    updateBuilder.wheres("name = rollen");
    assertEquals("UPDATE Person SET name = rollen, age = 20 WHERE name = rollen", updateBuilder.toString());

    updateBuilder.wheres("age = 12");
    assertEquals("UPDATE Person SET name = rollen, age = 20 WHERE name = rollen AND age = 12", updateBuilder.toString());
--------------------------------------------------------------------------------------
