# Despliegue en Render con Docker

## Archivos necesarios
- ✅ `Dockerfile` - Configuración de contenedor Docker
- ✅ `.dockerignore` - Archivos excluidos del contexto Docker
- ✅ `render.yaml` - Configuración de servicio en Render (opcional)

## Pasos para desplegar

### Opción 1: Desde el Dashboard de Render

1. **Ir a [Render Dashboard](https://dashboard.render.com/)**
2. **Crear nuevo Web Service**
   - Click en "New +" → "Web Service"
   - Conectar tu repositorio GitHub

3. **Configurar el servicio**
   - **Name**: `gamereviewer-backend`
   - **Region**: Oregon (mismo que tu BD)
   - **Branch**: `main`
   - **Runtime**: Docker
   - **Dockerfile Path**: `./Dockerfile`
   - **Plan**: Free

4. **Variables de entorno** (Add Environment Variables)
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d5l9lhogjchc7382b1v0-a.oregon-postgres.render.com/gamereviewer_db?sslmode=require
   SPRING_DATASOURCE_USERNAME=dandev
   SPRING_DATASOURCE_PASSWORD=I7yD82gsblXi2gdfzr9PG3ZQC2XgfIJD
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SERVER_PORT=9090
   JAVA_OPTS=-Xmx512m -Xms256m
   ```

5. **Health Check Path**: `/actuator/health`

6. **Deploy!**

### Opción 2: Usando render.yaml (IaC)

Si prefieres Infrastructure as Code, Render detectará automáticamente el archivo `render.yaml` y configurará todo por ti. Solo necesitas:

1. Conectar el repositorio
2. Configurar manualmente la contraseña de BD (por seguridad no la incluyo en el YAML)

## Pruebas locales con Docker

```bash
# Construir la imagen
docker build -t gamereviewer-backend .

# Ejecutar localmente
docker run -p 9090:9090 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://dpg-d5l9lhogjchc7382b1v0-a.oregon-postgres.render.com/gamereviewer_db?sslmode=require" \
  -e SPRING_DATASOURCE_USERNAME="dandev" \
  -e SPRING_DATASOURCE_PASSWORD="I7yD82gsblXi2gdfzr9PG3ZQC2XgfIJD" \
  gamereviewer-backend
```

## Verificar el despliegue

Una vez desplegado, verifica:
- Health check: `https://tu-app.onrender.com/actuator/health`
- API docs: `https://tu-app.onrender.com/swagger-ui.html`
- Endpoint de usuarios: `https://tu-app.onrender.com/api/usuarios`

## Características del Dockerfile

- ✅ **Multi-stage build**: Reduce tamaño final de la imagen
- ✅ **Alpine Linux**: Imagen base ligera
- ✅ **Usuario no-root**: Seguridad mejorada
- ✅ **Cache de dependencias Maven**: Builds más rápidos
- ✅ **JRE 21**: Solo runtime, sin JDK completo
- ✅ **Variables de entorno configurables**

## Solución de problemas

### La aplicación no inicia
- Verifica las variables de entorno en Render Dashboard
- Revisa los logs: Click en tu servicio → Logs tab

### Error de conexión a base de datos
- Asegúrate de que `sslmode=require` esté en la URL
- Verifica que el servicio esté en la misma región (Oregon) que la BD

### Out of Memory
- Ajusta `JAVA_OPTS` a `-Xmx256m -Xms128m` en el plan Free
- Considera upgradar si necesitas más recursos

## Nota importante

El plan Free de Render:
- ⚠️ Se duerme después de 15 minutos de inactividad
- ⚠️ Primera request después de dormir toma ~30-60 segundos
- ✅ Perfecto para desarrollo y demos
- ✅ 750 horas gratis al mes
