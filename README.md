# Taller backend spring

Para configurar la db postgres en docker ejecutar el siguiente comando (previamente se debe tener instalada una imagen
de postgres):

            docker run --name some-postgres -e POSTGRES_PASSWORD=guarito123# -p 5432:5432 -d postgres

## Endpoints creados

### Login de usuario (```POST: /api/auth/register```)

Endpoint público para crear un nuevo usuario, retorna el JWT para poder acceder a rutas protegidas. A continuación un
ejemplo de un posible body para probar el endpoint.

```json
{
  "email": "gcornejo@mail.com",
  "password": "123",
  "name": "gabriel",
  "company": "bancolombia"
}
```

### Registro de usuario (```POST: /api/auth/login```)

Endpoint público creado para iniciar sesión y obtener el JWT que permite acceder a rutas protegidas.

```json
{
  "email": "gcornejo@mail.com",
  "password": "123"
}
```

### Obtener perfil del usuario (```GET: /api/profile```)

Endpoint protegido, requiere un token JWT válido en el header Authorization. Retorna los datos del perfil del usuario al
que corresponda el JWT.

```
// No requiere body.
```

### Actualizar perfil del usuario (```PUT: /api/profile```)

Endpoint protegido, requiere un JWT válido en el header Authorization. Recibe un body con los nuevos valores del perfil,
reemplaza estos valores en
el registro del usuario al que corresponda el JWT.

```json
{
  "name": "gabo",
  "company": "Bancolombia2"
}
```