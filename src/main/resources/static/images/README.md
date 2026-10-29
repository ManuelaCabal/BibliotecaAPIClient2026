# Imágenes del proyecto

Coloca aquí las imágenes locales usadas por las listas.

## Estructura

- `autores/` → fotos de autores (`autor.foto`)
- `libros/` → portadas de libros (`libro.portada`)
- `paises/` → banderas (`pais.bandera`)
- `premios/` → logos de premios (`premio.logotipo`)

## Cómo se resuelve cada imagen

Si el valor viene como URL (`http://` o `https://`), se usa esa URL directamente.

Si el valor viene como nombre de archivo, se busca en su carpeta correspondiente.

Ejemplos:

- `autor.foto = "gabriel-garcia-marquez.jpg"` → `/images/autores/gabriel-garcia-marquez.jpg`
- `pais.bandera = "colombia.png"` → `/images/paises/colombia.png`
- `libro.portada = "cien-anos.jpg"` → `/images/libros/cien-anos.jpg`
- `premio.logotipo = "nobel.svg"` → `/images/premios/nobel.svg`
