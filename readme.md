GET
http://localhost:8080/SpringBootWeb/hi/cesar

Hello cesar from a JSP page
{ "hello": "Cesar Chavez" }
{ "hello": "Cesar" }
{ "hello": "Cesar Chavez" }
{ "hello": "Cesar" }

GET
http://localhost:8080/SpringBootWeb/hi2/cesar

Hello cesar from a JSP page
{ "hello": "Cesar Chavez" }
{ "hello": "Cesar" }
{ "hello": "Cesar Chavez" }
{ "hello": "Cesar" }

GET
http://localhost:8080/SpringBootWeb/get/hello?name=Cesar
{ "hello": "Cesar" }

POST
var jsonObj = {
	name : "Cesar Chavez"
};
http://localhost:8080/SpringBootWeb/post/hello
{ "hello": "Cesar Chavez" }

PUT
var jsonObj = {
	name : "Cesar Chavez"
};
http://localhost:8080/SpringBootWeb/put/hello
{ "hello": "Cesar Chavez" }

DELETE
http://localhost:8080/SpringBootWeb/delete/hello?name=Cesar
{ "hello": "Cesar" }


GET
http://localhost:8080/SpringBootWeb/teams

{
  "_embedded" : {
    "teams" : [ {
      "name" : "Peanuts",
      "location" : "California",
      "mascotte" : null,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/SpringBootWeb/teams/1"
        },
        "team" : {
          "href" : "http://localhost:8080/SpringBootWeb/teams/1"
        },
        "players" : {
          "href" : "http://localhost:8080/SpringBootWeb/teams/1/players"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/SpringBootWeb/teams"
    },
    "profile" : {
      "href" : "http://localhost:8080/SpringBootWeb/profile/teams"
    },
    "search" : {
      "href" : "http://localhost:8080/SpringBootWeb/teams/search"
    }
  }
}

GET
http://localhost:8080/SpringBootWeb/players

{
  "_embedded" : {
    "players" : [ {
      "name" : "Snoopy",
      "position" : "shortstop",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/SpringBootWeb/players/1"
        },
        "player" : {
          "href" : "http://localhost:8080/SpringBootWeb/players/1"
        }
      }
    }, {
      "name" : "Charlie Brown",
      "position" : "pitcher",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/SpringBootWeb/players/2"
        },
        "player" : {
          "href" : "http://localhost:8080/SpringBootWeb/players/2"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/SpringBootWeb/players"
    },
    "profile" : {
      "href" : "http://localhost:8080/SpringBootWeb/profile/players"
    },
    "search" : {
      "href" : "http://localhost:8080/SpringBootWeb/players/search"
    }
  }
}

GET
http://localhost:8080/SpringBootWeb/teams1/Peanuts

<team>
	<location>California</location>
	<name>Peanuts</name>
	<players>
		<name>Snoopy</name>
		<position>shortstop</position>
	</players>
	<players>
		<name>Charlie Brown</name>
		<position>pitcher</position>
	</players>
</team>

GET
http://localhost:8080/SpringBootWeb/teams2/Peanuts

<team>
	<location>California</location>
	<name>Peanuts</name>
	<players>
		<name>Snoopy</name>
		<position>shortstop</position>
	</players>
	<players>
		<name>Charlie Brown</name>
		<position>pitcher</position>
	</players>
</team>
