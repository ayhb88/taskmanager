# Task Manager
Aplicación de gestión de tareas. Permite a los usuarios crear
nuevas tareas y ver la lista de tareas existentes.

Basado en:
- Framework Spring Boot y siguiendo.
- Arquitectura hexagonal.
- Diseño dirigido por el dominio (DDD)
- Desarrollo dirigido por pruebas de aceptación (ATDD)

## *Ejecucion de pruebas de aceptación y pruebas unitarias*
	$ ./gradlew :test

## *Construir Jar de la app*
	$ ./gradlew build

## *Crear imagen Docker*
	$ ./build.sh

## *Desplegar imagen docker*
	$ docker-compose -f docker-compose.yml up

# Peticiones

- ## POST
### Creación de una tarea
> http://localhost:8080/
- Request
```
{
	"name":"test1",
	"description":"desc1",
	"dueDate":"2024-12-01"
}
```
- Response
```
{
	"idTask": "af4a99ac-991f-4e51-8135-7c8bc125ad48",
	"name": "test1",
	"description": "desc1",
	"dueDate": "2024-12-01",
	"createdAt": "2024-05-27T01:01:10.277431624Z",
	"updatedDate": "2024-05-27T01:01:10.277432024Z",
	"status": "TO_DO"
}
```

- ## GET
### Listar todas las tareas
> http://localhost:8080/tasks
- Response
```
[
	{
		"idTask": "af4a99ac-991f-4e51-8135-7c8bc125ad48",
		"name": "test1",
		"description": "desc1",
		"dueDate": "2024-12-01",
		"createdAt": "2024-05-27T01:01:10.277Z",
		"updatedDate": "2024-05-27T01:01:10.277Z",
		"status": "TO_DO"
	},
	{
		"idTask": "b95889f6-e26c-43d0-8283-6b92a5eb076e",
		"name": "test2",
		"description": "desc2",
		"dueDate": "2024-05-30",
		"createdAt": "2024-05-27T01:02:19.774Z",
		"updatedDate": "2024-05-27T01:02:19.774Z",
		"status": "TO_DO"
	}
]
```

### Obtener una tarea por **ID**
> http://localhost:8080/task/{id}

- Request
```
{
	"id":"7bd837d8-971f-4b10-9e98-c895295f23a0"
}
```
- Response
```
{
	"idTask": "7bd837d8-971f-4b10-9e98-c895295f23a0",
	"name": "test1",
	"description": "desc1",
	"dueDate": "2024-12-01",
	"createdAt": "2024-05-26T20:23:00.158Z",
	"updatedDate": "2024-05-26T20:23:00.158Z",
	"status": "TO_DO"
}
```
