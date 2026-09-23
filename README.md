# UEES - Programación Orientada a Objetos - Java

Proyecto desarrollado como parte de la asignatura de **Programación Orientada a Objetos** de la Universidad de Especialidades Espíritu Santo (UEES).

El proyecto evoluciona incrementalmente durante las diferentes semanas de la asignatura.

El sistema modela una empresa proveedora de tecnología y capacitación que puede atender tanto a personas naturales bajo un modelo B2C como a empresas bajo un modelo B2B.

La implementación actual incorpora:

- Programación Orientada a Objetos
- encapsulación
- asociación
- herencia
- composición
- abstracción
- clases abstractas
- polimorfismo
- excepciones
- colecciones
- persistencia
- SQLite
- JDBC
- operaciones CRUD
- JavaFX
- transacciones
- integridad referencial

---

# Objetivo

Aplicar progresivamente los principios de Programación Orientada a Objetos mediante el desarrollo de un sistema de gestión de:

- clientes;
- productos;
- inventario;
- proformas.

El proyecto busca separar las responsabilidades del sistema entre:

- modelo de dominio;
- reglas de negocio;
- servicios;
- persistencia;
- interfaz gráfica.

---

# Evolución del proyecto

## Semana 1 - Encapsulación y asociación

Durante la Semana 1 se implementaron las clases iniciales:

- `Producto`
- `Cliente`

### Conceptos aplicados

- clases y objetos;
- encapsulación;
- atributos privados;
- constructores;
- getters y setters;
- asociación entre objetos.

La clase `Producto` representa los artículos o servicios comercializados por la empresa.

La clase `Cliente` representa a los compradores registrados en el sistema.

---

## Semana 2 - Herencia y composición

Durante la Semana 2 se amplió el modelo incorporando herencia y composición.

### Herencia de productos

La clase `Producto` funciona como clase base para:

- `ProductoFisico`
- `ProductoDigital`

### ProductoFisico

Incorpora información adicional como:

- peso;
- ubicación de almacenamiento.

### ProductoDigital

Incorpora información como:

- tamaño en MB;
- URL de descarga.

### Composición

La clase `Proforma` contiene una colección de objetos:

```java
ItemProforma
```

Cada `ItemProforma` relaciona:

- un producto;
- una cantidad;
- un subtotal.

Conceptualmente:

```text
Proforma
   |
   +---- ItemProforma
   |         |
   |         +---- Producto
   |
   +---- ItemProforma
             |
             +---- Producto
```

---

## Semana 3 - Abstracción y polimorfismo

Durante la Semana 3 se incorporaron:

- abstracción;
- clases abstractas;
- sobrescritura;
- polimorfismo.

### Cliente como clase abstracta

La clase `Cliente` fue definida como abstracta:

```java
public abstract class Cliente
```

Además establece el contrato:

```java
public abstract double calcularDescuento();
```

Cada tipo de cliente implementa su propia política de descuento.

### ClienteMayorista

Implementa su propia versión de:

```java
calcularDescuento()
```

### ClienteMinorista

Implementa también su propia política de descuento.

Actualmente el descuento correspondiente al cliente minorista es:

```text
5 %
```

### Polimorfismo

La clase `Proforma` utiliza una referencia:

```java
private Cliente cliente;
```

y para determinar el descuento ejecuta:

```java
cliente.calcularDescuento();
```

Por tanto, `Proforma` no necesita preguntar mediante condicionales qué tipo de cliente está utilizando.

El comportamiento depende del objeto concreto durante la ejecución.

---

## Semana 4 - OCP, validaciones y excepciones

Durante la Semana 4 se amplió el sistema incorporando nuevas reglas de negocio.

### ClienteCorporativo

Se incorporó:

```java
ClienteCorporativo
```

que hereda de:

```java
Cliente
```

Su implementación del descuento es:

```java
@Override
public double calcularDescuento() {
    return descuento;
}
```

Actualmente el descuento corporativo corresponde a:

```text
10 %
```

La incorporación de un nuevo cliente sin alterar la lógica general de `Proforma` permite demostrar el principio:

```text
Open/Closed Principle
```

El sistema queda abierto a extensión mediante nuevas clases, pero evita modificar innecesariamente las clases existentes.

### Jerarquía de clientes

```text
                 Cliente
               <<abstract>>
                    |
       +------------+------------+
       |            |            |
       v            v            v
ClienteMayorista ClienteMinorista ClienteCorporativo
```

---

## Validaciones de Producto

La clase `Producto` incorpora validaciones para evitar estados inválidos.

Actualmente se verifica:

- nombre vacío;
- precio negativo;
- stock negativo;
- cantidad igual a cero;
- cantidad negativa;
- cantidad superior al stock disponible.

El método:

```java
venderUnidades(int cantidad)
```

valida primero la cantidad solicitada.

Una cantidad menor o igual a cero produce:

```java
IllegalArgumentException
```

Si la cantidad supera el stock disponible se produce:

```java
StockInsuficienteException
```

---

## Excepción personalizada

Se incorporó:

```java
StockInsuficienteException
```

Esta excepción representa explícitamente una regla de negocio del dominio.

Ejemplo conceptual:

```text
Stock disponible = 5
Cantidad solicitada = 6

Resultado:
StockInsuficienteException
```

Una operación fallida no modifica el stock existente.

---

# Semana 5 - Colecciones, servicios y persistencia

Durante esta etapa el proyecto evolucionó desde un modelo ejecutado principalmente en memoria hacia una aplicación con persistencia.

Se incorporaron paquetes específicos para:

```text
modelo
servicio
persistencia
ui
```

---

## Colecciones

El catálogo de productos utiliza estructuras de datos de Java.

### ArrayList

Permite mantener una colección ordenada de productos.

```java
List<Producto>
```

### HashMap

Permite localizar un producto mediante su código.

Conceptualmente:

```text
P001 -> Laptop Pro
P002 -> Teclado Mecanico RGB
```

### HashSet

Mantiene el conjunto de códigos registrados y permite detectar duplicados.

Estas estructuras son utilizadas dentro de:

```java
CatalogoProductos
```

---

# Capa de servicio

Se implementaron:

```text
CatalogoProductos
CatalogoClientes
```

Estas clases permiten separar las operaciones de negocio de la interfaz gráfica.

La estructura general es:

```text
Interfaz
   |
   v
Servicios
   |
   v
Persistencia
   |
   v
SQLite
```

---

# Persistencia SQLite

La aplicación utiliza SQLite como base de datos local.

La conexión está encapsulada en:

```java
ConexionSQLite
```

La aplicación crea automáticamente:

```text
data/
```

cuando necesita acceder a la base.

La base utilizada durante la ejecución se denomina:

```text
proformas.db
```

El archivo de base de datos local no se versiona en GitHub.

---

# Tablas

Actualmente se utilizan cuatro tablas principales:

```text
clientes
productos
proformas
detalle_proforma
```

---

## Tabla clientes

Almacena los clientes registrados.

Campos:

```text
email
nombre
ciudad
tipo
```

Clave primaria:

```text
email
```

---

## Tabla productos

Almacena los productos disponibles.

Campos:

```text
codigo
nombre
precio
stock
tipo
peso
ubicacion_almacen
tamano_mb
url_descarga
```

Clave primaria:

```text
codigo
```

Los campos opcionales permiten representar diferentes tipos de producto.

---

## Tabla proformas

Representa la cabecera de una proforma.

Campos:

```text
id
cliente_email
fecha
subtotal
descuento
total
```

Relación:

```text
proformas.cliente_email
        |
        v
clientes.email
```

---

## Tabla detalle_proforma

Representa los productos pertenecientes a cada proforma.

Campos:

```text
id
proforma_id
producto_codigo
cantidad
precio_unitario
subtotal
```

Relaciones:

```text
detalle_proforma.proforma_id
        |
        v
proformas.id
```

y:

```text
detalle_proforma.producto_codigo
        |
        v
productos.codigo
```

---

# Modelo cabecera-detalle

Una proforma se almacena utilizando un modelo cabecera-detalle.

```text
Cliente
   |
   v
Proforma
   |
   +---------+---------+
   |                   |
   v                   v
Detalle               Detalle
   |                   |
   v                   v
Producto             Producto
```

Por ejemplo:

```text
Proforma #1
    |
    +--- P001 Laptop Pro

Proforma #2
    |
    +--- P001 Laptop Pro
    |
    +--- P002 Teclado Mecanico RGB
```

Cada detalle contiene su propio:

- producto;
- cantidad;
- precio unitario;
- subtotal.

---

# Repositorios

La persistencia fue separada mediante clases Repository.

Actualmente se implementan:

```text
RepositorioClientesSQLite
RepositorioProductosSQLite
RepositorioProformasSQLite
```

Su responsabilidad consiste en ejecutar las operaciones necesarias contra SQLite sin trasladar SQL directamente a las vistas JavaFX.

---

# Transacciones

El almacenamiento de una proforma utiliza una transacción.

El flujo general es:

```text
BEGIN
  |
  v
INSERT proformas
  |
  v
obtener ID
  |
  v
INSERT detalle_proforma
  |
  v
COMMIT
```

Si ocurre una excepción:

```text
ROLLBACK
```

Esto evita dejar una cabecera registrada sin sus detalles asociados.

Conceptualmente:

```text
Todo correcto
      |
      v
    COMMIT
```

o:

```text
Ocurre error
      |
      v
   ROLLBACK
```

---

# Integridad referencial

SQLite requiere habilitar las claves foráneas para cada conexión.

Por esta razón `ConexionSQLite` ejecuta:

```sql
PRAGMA foreign_keys = ON;
```

La aplicación comprueba posteriormente el estado mediante:

```sql
PRAGMA foreign_keys;
```

La conexión solamente continúa si el resultado es:

```text
1
```

Esto garantiza que las operaciones realizadas por la aplicación respeten las claves foráneas.

---

## Relaciones protegidas

Actualmente existen las relaciones:

```text
proformas.cliente_email
    -> clientes.email
```

```text
detalle_proforma.proforma_id
    -> proformas.id
```

```text
detalle_proforma.producto_codigo
    -> productos.codigo
```

Durante las pruebas también se utilizó:

```sql
PRAGMA foreign_key_check;
```

para verificar la consistencia de las relaciones.

---

## Semana 6 - Interfaz gráfica y manejo de eventos

Durante la Semana 6 se integró el catálogo desarrollado en la Semana 5 con una interfaz gráfica implementada mediante **JavaFX**.

El objetivo de esta etapa fue permitir que el usuario pueda interactuar con las operaciones del sistema desde una interfaz gráfica, incorporando:

- interfaz gráfica con JavaFX;
- operaciones CRUD desde la interfaz;
- manejo de eventos;
- validación de datos de entrada;
- mensajes de confirmación y error;
- integración con las colecciones desarrolladas en la Semana 5;
- integración con la capa de servicios;
- persistencia mediante SQLite;
- navegación entre los módulos del sistema.

### Tecnología utilizada

Para la interfaz gráfica se utilizó:

```text
JavaFX

## Vista de clientes

La vista:

```text
VistaClientes
```

permite administrar los clientes del sistema.

Operaciones disponibles:

- agregar;
- buscar;
- actualizar;
- eliminar;
- listar;
- limpiar formulario.

También permite seleccionar el tipo de cliente.

El sistema rechaza clientes que utilicen un correo electrónico previamente registrado.

---

# Vista de productos

La vista:

```text
VistaProductos
```

permite administrar el catálogo.

Operaciones disponibles:

- agregar;
- buscar;
- actualizar;
- eliminar;
- listar;
- limpiar formulario.

El código funciona como identificador único.

El sistema rechaza códigos duplicados.

---

# Vista de proformas

La vista:

```text
VistaProformas
```

permite construir una proforma utilizando los clientes y productos persistidos.

Flujo:

```text
Buscar cliente
      |
      v
Ingresar código producto
      |
      v
Ingresar cantidad
      |
      v
Validar stock
      |
      v
Agregar item
      |
      v
Calcular subtotal
      |
      v
Calcular descuento
      |
      v
Calcular total
      |
      v
Guardar proforma
```

---

# Cálculo del descuento

El porcentaje de descuento no está implementado mediante una estructura condicional central.

La lógica utiliza:

```java
cliente.calcularDescuento();
```

Por tanto, el comportamiento se obtiene mediante polimorfismo.

Cada subclase de `Cliente` puede implementar una política distinta.

---

# Proformas guardadas

Después de guardar una proforma, la aplicación recupera las operaciones almacenadas en SQLite.

Para representar la información resumida se utiliza:

```java
ProformaResumen
```

La tabla presenta:

```text
ID
Cliente
Fecha
Subtotal
Descuento
Total
```

---

# Ver detalle

Se incorporó la funcionalidad:

```text
Ver detalle
```

que permite seleccionar una proforma almacenada y recuperar exclusivamente sus productos.

Para representar las filas se utiliza:

```java
DetalleProformaResumen
```

La consulta utiliza:

```sql
WHERE d.proforma_id = ?
```

por lo que cada consulta se limita al ID seleccionado.

Ejemplo:

```text
Proforma #1

P001
Laptop Pro
Cantidad: 1
```

mientras:

```text
Proforma #2

P001
Laptop Pro
Cantidad: 1

P002
Teclado Mecanico RGB
Cantidad: 2
```

Esto confirma que los detalles pertenecientes a distintas proformas permanecen separados.

---

# QA funcional

Durante el desarrollo se realizaron pruebas manuales de reglas de negocio.

| Prueba | Resultado esperado |
|---|---|
| Cliente inexistente | Rechazado |
| Producto inexistente | Rechazado |
| Cantidad 0 | Rechazada |
| Cantidad negativa | Rechazada |
| Cantidad mayor al stock | Rechazada |
| Proforma sin cliente | No se guarda |
| Proforma sin productos | No se guarda |
| Cliente duplicado | Rechazado |
| Producto duplicado | Rechazado |
| Actualización de producto | Persistida |
| Eliminación de producto | Persistida |
| Consulta detalle ID 1 | Recupera únicamente sus productos |
| Consulta detalle ID 2 | Recupera únicamente sus productos |
| Foreign Keys | Activadas |
| Integridad referencial | Verificada |
| Error durante transacción | Rollback |

---

# Pruebas de integridad SQLite

Se verificaron las relaciones utilizando consultas como:

```sql
PRAGMA foreign_keys;
```

Resultado esperado:

```text
1
```

También:

```sql
PRAGMA foreign_key_check;
```

Un resultado vacío indica que no existen violaciones pendientes.

---

# Arquitectura actual

```text
┌─────────────────────────────────┐
│             JavaFX              │
│                                 │
│ AplicacionJavaFX                │
│ VistaClientes                   │
│ VistaProductos                  │
│ VistaProformas                  │
└────────────────┬────────────────┘
                 │
                 v
┌─────────────────────────────────┐
│            Servicios            │
│                                 │
│ CatalogoClientes                │
│ CatalogoProductos               │
└────────────────┬────────────────┘
                 │
                 v
┌─────────────────────────────────┐
│        Modelo de dominio        │
│                                 │
│ Cliente                         │
│ ClienteMayorista                │
│ ClienteMinorista                │
│ ClienteCorporativo              │
│ Producto                        │
│ ProductoFisico                  │
│ ProductoDigital                 │
│ ItemProforma                    │
│ Proforma                        │
└────────────────┬────────────────┘
                 │
                 v
┌─────────────────────────────────┐
│          Persistencia           │
│                                 │
│ ConexionSQLite                  │
│ RepositorioClientesSQLite       │
│ RepositorioProductosSQLite      │
│ RepositorioProformasSQLite      │
└────────────────┬────────────────┘
                 │
                 v
┌─────────────────────────────────┐
│             SQLite              │
│                                 │
│ clientes                        │
│ productos                       │
│ proformas                       │
│ detalle_proforma                │
└─────────────────────────────────┘
```

---

# Estructura de paquetes

```text
src/main/java/ec/edu/uees/proformas
│
├── Proformas.java
│
├── excepciones
│   └── StockInsuficienteException.java
│
├── modelo
│   ├── Cliente.java
│   ├── ClienteMayorista.java
│   ├── ClienteMinorista.java
│   ├── ClienteCorporativo.java
│   ├── Producto.java
│   ├── ProductoFisico.java
│   ├── ProductoDigital.java
│   ├── ItemProforma.java
│   ├── Proforma.java
│   ├── ProformaResumen.java
│   └── DetalleProformaResumen.java
│
├── persistencia
│   ├── ConexionSQLite.java
│   ├── RepositorioClientesSQLite.java
│   ├── RepositorioProductosSQLite.java
│   └── RepositorioProformasSQLite.java
│
├── servicio
│   ├── CatalogoClientes.java
│   └── CatalogoProductos.java
│
└── ui
    ├── AplicacionJavaFX.java
    ├── VistaClientes.java
    ├── VistaProductos.java
    └── VistaProformas.java
```

---

# Diagramas UML

## Semana 1

![UML Semana 1](docs/uml/producto_cliente.png)

## Semana 2

![UML Semana 2](docs/uml/uml_semana2_herencia_composicion.drawio.png)

## Semana 3

![UML Semana 3](docs/uml/uml_semana3_polimorfismo.png)

El archivo editable se encuentra en:

```text
docs/uml/uml_semana3_polimorfismo.drawio
```

En entregas posteriores se incorporarán diagramas actualizados según los nuevos requerimientos de la asignatura.

---

# Evidencias anteriores

## Semana 3 - Cliente abstracto

![Cliente abstracto](docs/evidencias/semana3/java/windows/01a_cliente_clase_abstracta_netbeans.png)

## Método calcularDescuento

![Metodo abstracto](docs/evidencias/semana3/java/windows/01b_cliente_metodo_abstracto_netbeans.png)

## ClienteMayorista

![ClienteMayorista](docs/evidencias/semana3/java/windows/02_cliente_mayorista_netbeans.png)

## ClienteMinorista

![ClienteMinorista](docs/evidencias/semana3/java/windows/03_cliente_minorista_netbeans.png)

## Polimorfismo

![Polimorfismo](docs/evidencias/semana3/java/windows/04_polimorfismo_netbeans.png)

## Ejecución Windows

![Windows](docs/evidencias/semana3/java/windows/05_prueba_powershell_windows.png)

## Fedora

![Fedora](docs/evidencias/semana3/java/fedora/06_fedora_entorno_y_branch_java.png)

![Prueba Fedora](docs/evidencias/semana3/java/fedora/07_prueba_java_fedora.png)

---

# Compilación

El proyecto utiliza Maven.

Desde la raíz:

```bash
mvn clean install
```

La compilación actual fue comprobada mediante Apache NetBeans obteniendo:

```text
BUILD SUCCESS
```

---

# Ejecución de JavaFX

Desde la raíz del proyecto:

```bash
mvn javafx:run
```

El punto de entrada configurado para JavaFX es:

```text
ec.edu.uees.proformas.ui.AplicacionJavaFX
```

---

# Clase Proformas

La clase:

```text
ec.edu.uees.proformas.Proformas
```

se conserva porque contiene pruebas y ejemplos correspondientes a etapas anteriores del proyecto.

Esto permite preservar la evolución académica realizada durante las diferentes semanas.

---

# Dependencias

El proyecto utiliza Maven para administrar dependencias.

Actualmente incluye:

```text
JavaFX Controls
SQLite JDBC
JavaFX Maven Plugin
```

---

# Tecnologías utilizadas

- Java 25
- JavaFX 25.0.1
- Maven
- SQLite
- JDBC
- SQLite JDBC
- Apache NetBeans
- DBeaver
- Programación Orientada a Objetos
- UML
- diagrams.net
- Git
- GitHub
- Windows PowerShell
- Fedora Linux

---

# Conceptos aplicados

Durante la evolución del proyecto se han aplicado:

- clases;
- objetos;
- encapsulación;
- asociación;
- herencia;
- composición;
- abstracción;
- clases abstractas;
- métodos abstractos;
- sobrescritura;
- polimorfismo;
- enlace dinámico;
- OCP;
- excepciones;
- excepciones personalizadas;
- validaciones;
- colecciones;
- `ArrayList`;
- `HashMap`;
- `HashSet`;
- JDBC;
- persistencia;
- patrón Repository;
- operaciones CRUD;
- transacciones;
- commit;
- rollback;
- claves primarias;
- claves foráneas;
- modelo cabecera-detalle;
- interfaz gráfica JavaFX.

---

# Estado del proyecto

La implementación actual se encuentra en la rama:

```text
fase-03-interfaz-java
```

Esta rama incorpora la evolución desde el modelo inicial de Programación Orientada a Objetos hasta una aplicación gráfica con persistencia.

El proyecto **continúa en desarrollo**.

Las siguientes actividades y entregas académicas serán incorporadas incrementalmente conforme avancen los contenidos de la asignatura.

---

# Autor

**Sergio Méndez**
