# UEES - Programación Orientada a Objetos - Java

Proyecto desarrollado como parte de la asignatura de Programación Orientada a Objetos.

El sistema modela una empresa proveedora de tecnología y capacitación que puede atender tanto a personas naturales bajo un modelo B2C como a empresas bajo un modelo B2B.

## Objetivo

Aplicar los fundamentos de Programación Orientada a Objetos mediante el modelado de clientes, productos y proformas utilizando encapsulación, asociación, herencia, composición, abstracción, sobrescritura de métodos y polimorfismo.

---

## Semana 1 - Encapsulación y asociación

Durante la Semana 1 se implementaron las clases:

- `Producto`
- `Cliente`

### Conceptos aplicados

- Clases y objetos
- Encapsulación
- Atributos privados
- Getters y setters
- Asociación entre objetos

La clase `Producto` representa los artículos o servicios comercializados por la empresa.

La clase `Cliente` representa al comprador del sistema.

---

## Semana 2 - Herencia y composición

Durante la Semana 2 se amplió el modelo incorporando herencia y composición.

### Herencia

La clase `Producto` funciona como clase base para:

- `ProductoFisico`
- `ProductoDigital`

`ProductoFisico` incorpora atributos específicos como:

- peso
- ubicación de almacenamiento

`ProductoDigital` incorpora atributos como:

- tamaño en MB
- URL de descarga

### Composición

La clase `Proforma` contiene una colección de objetos `ItemProforma`.

Cada `ItemProforma` relaciona:

- un producto
- una cantidad
- el cálculo del subtotal

La clase `Proforma` permite agregar diferentes ítems y calcular el total de la operación comercial.

---

## Semana 3 - Polimorfismo, interfaces y clases abstractas

Durante la Semana 3 se extendió el sistema incorporando abstracción, sobrescritura de métodos y polimorfismo.

### Clase abstracta Cliente

La clase `Cliente` fue transformada en una clase abstracta:

```java
public abstract class Cliente
```

Además, define el método abstracto:

```java
public abstract double calcularDescuento();
```

Esto establece un contrato que debe ser implementado por las clases concretas que heredan de `Cliente`.

### ClienteMayorista

La clase `ClienteMayorista` hereda de `Cliente` y sobrescribe el método `calcularDescuento()`.

```java
@Override
public double calcularDescuento() {
    return 0.20;
}
```

El descuento aplicado es del **20 %**.

### ClienteMinorista

La clase `ClienteMinorista` también hereda de `Cliente` y proporciona su propia implementación de `calcularDescuento()`.

```java
@Override
public double calcularDescuento() {
    return 0.05;
}
```

El descuento aplicado es del **5 %**.

### Polimorfismo

La clase `Proforma` mantiene una referencia de tipo:

```java
private Cliente cliente;
```

Para calcular el total se ejecuta:

```java
double descuento = cliente.calcularDescuento();
return subtotal * (1 - descuento);
```

El método ejecutado depende del tipo real del objeto almacenado en `cliente`.

Por esta razón, `Proforma` no necesita utilizar condicionales para determinar si el cliente es mayorista o minorista.

El comportamiento se determina dinámicamente mediante polimorfismo.

### Resultados

Para un subtotal de:

```text
Laptop:    $850.00
Curso:     $240.00
------------------
Subtotal: $1090.00
```

se obtienen los siguientes resultados:

| Tipo de cliente | Descuento | Total |
|---|---:|---:|
| ClienteMayorista | 20 % | $872.00 |
| ClienteMinorista | 5 % | $1035.50 |

Esto demuestra que la misma operación:

```java
cliente.calcularDescuento()
```

produce comportamientos diferentes dependiendo del tipo concreto del objeto.

---

## Clases principales

- `Cliente` — clase abstracta
- `ClienteMayorista`
- `ClienteMinorista`
- `Producto`
- `ProductoFisico`
- `ProductoDigital`
- `ItemProforma`
- `Proforma`

---

## Modelo general

```text
                    Cliente
                   <<abstract>>
                  /           \
                 /             \
                v               v
      ClienteMayorista    ClienteMinorista
              |                  |
              |                  |
              +--------+---------+
                       |
                       v
                    Proforma
                       |
                       v
                  ItemProforma
                       |
                       v
                    Producto
                   /        \
                  /          \
                 v            v
        ProductoFisico   ProductoDigital
```

---

# Diagramas UML

## Semana 1

![UML Semana 1](docs/uml/producto_cliente.png)

## Semana 2

![UML Semana 2](docs/uml/uml_semana2_herencia_composicion.drawio.png)

## Semana 3

El diagrama de Semana 3 incorpora:

- clase abstracta `Cliente`
- método abstracto `calcularDescuento()`
- `ClienteMayorista`
- `ClienteMinorista`
- generalización
- sobrescritura
- polimorfismo
- relaciones con `Proforma`
- composición con `ItemProforma`
- jerarquía de productos

![UML Semana 3](docs/uml/uml_semana3_polimorfismo.png)

El archivo editable de diagrams.net también se encuentra en:

```text
docs/uml/uml_semana3_polimorfismo.drawio
```

---

# Evidencias - Semana 3 Java

## 1. Cliente como clase abstracta

Declaración de `Cliente` como clase abstracta:

![Cliente abstracto](docs/evidencias/semana3/java/windows/01a_cliente_clase_abstracta_netbeans.png)

Método abstracto `calcularDescuento()`:

![Método abstracto](docs/evidencias/semana3/java/windows/01b_cliente_metodo_abstracto_netbeans.png)

## 2. ClienteMayorista

Implementación de `ClienteMayorista` con descuento del 20 %:

![ClienteMayorista](docs/evidencias/semana3/java/windows/02_cliente_mayorista_netbeans.png)

## 3. ClienteMinorista

Implementación de `ClienteMinorista` con descuento del 5 %:

![ClienteMinorista](docs/evidencias/semana3/java/windows/03_cliente_minorista_netbeans.png)

## 4. Polimorfismo

Uso del método `calcularDescuento()` a través de la abstracción `Cliente`:

![Polimorfismo Java](docs/evidencias/semana3/java/windows/04_polimorfismo_netbeans.png)

## 5. Prueba en Windows PowerShell

Compilación y ejecución independiente del proyecto utilizando `javac` y `java`:

![Prueba Java Windows](docs/evidencias/semana3/java/windows/05_prueba_powershell_windows.png)

## 6. Entorno Java en Fedora

Verificación de la rama Git y del entorno Java/OpenJDK utilizado en Fedora Linux:

![Entorno Fedora Java](docs/evidencias/semana3/java/fedora/06_fedora_entorno_y_branch_java.png)

## 7. Prueba en Fedora Linux

Compilación y ejecución completa del proyecto en Fedora:

![Prueba Java Fedora](docs/evidencias/semana3/java/fedora/07_prueba_java_fedora.png)

Los resultados obtenidos en Windows y Fedora son equivalentes, demostrando la portabilidad de la implementación Java.

---

# Ejecución

## Apache NetBeans

La clase principal es:

```text
ec.edu.uees.proformas.Proformas
```

Puede ejecutarse directamente desde Apache NetBeans.

## Windows PowerShell

Desde la raíz del repositorio:

```powershell
$javaFiles = Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName }

Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue

javac -d out $javaFiles

java -cp out ec.edu.uees.proformas.Proformas
```

## Fedora Linux

```bash
rm -rf /tmp/uees-java-out

mkdir -p /tmp/uees-java-out

find src/main/java -name "*.java" -print0 | \
xargs -0 javac -d /tmp/uees-java-out

java -cp /tmp/uees-java-out ec.edu.uees.proformas.Proformas
```

---

## Ejemplo de ejecución Semana 3

```text
Descuento cliente mayorista: 20.0%
Descuento cliente minorista: 5.0%

Producto fisico: Laptop
Peso: 2.1 kg
Ubicacion: Bodega A

Producto digital: Curso Java
Tamano: 1500.0 MB
URL: https://ejemplo.com/curso

=== PROFORMA CLIENTE MAYORISTA ===
Cliente: Sergio
Descuento: 20.0%
Subtotal Laptop: $850.0
Subtotal Curso: $240.0
Total Proforma: $872.0

=== PROFORMA CLIENTE MINORISTA ===
Cliente: Ana
Descuento: 5.0%
Subtotal Laptop: $850.0
Subtotal Curso: $240.0
Total Proforma: $1035.5
```

---

## Tecnologías utilizadas

- Java
- Programación Orientada a Objetos
- Apache NetBeans
- JDK / OpenJDK
- Maven
- Windows PowerShell
- Fedora Linux
- UML
- diagrams.net
- Git
- GitHub

---

## Conceptos de Programación Orientada a Objetos aplicados

- Clases y objetos
- Encapsulación
- Asociación
- Herencia
- Composición
- Abstracción
- Clases abstractas
- Métodos abstractos
- Sobrescritura
- Polimorfismo
- Enlace dinámico

---

## Semana 4 - Validaciones, excepciones y extensión del modelo

Durante la Semana 4 se fortalecieron las reglas de negocio mediante validaciones y manejo de excepciones.

### Validaciones en Producto

La clase `Producto` valida sus propios atributos.

Entre las reglas implementadas se encuentran:

- el nombre no puede ser nulo ni estar vacío;
- el precio no puede ser negativo;
- el stock no puede ser negativo;
- una venta debe solicitar una cantidad mayor que cero;
- no se puede vender una cantidad superior al stock disponible.

Ejemplo:

```java
public void setPrecio(double precio) {
    if (precio < 0) {
        throw new IllegalArgumentException(
                "El precio no puede ser negativo."
        );
    }

    this.precio = precio;
}
```

### Excepción StockInsuficienteException

Se implementó una excepción específica para representar el intento de vender más unidades de las disponibles:

```java
public class StockInsuficienteException extends Exception {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
```

La clase `Producto` utiliza esta excepción dentro de:

```java
public void venderUnidades(int cantidad)
        throws StockInsuficienteException
```

Si la cantidad solicitada supera el stock disponible, la operación es rechazada.

### ClienteCorporativo

También se agregó una nueva especialización:

```java
ClienteCorporativo
```

que extiende de `Cliente` e implementa su propia política de descuento.

```java
@Override
public double calcularDescuento() {
    return descuento;
}
```

El descuento correspondiente al cliente corporativo es del **10 %**.

La incorporación de un nuevo tipo de cliente no requiere modificar la lógica de `Proforma`.

Esto mantiene el diseño abierto a nuevas extensiones mediante polimorfismo.

---

# Semana 5 - Colecciones y genéricos

Durante la Semana 5 se implementó un catálogo de productos utilizando las principales colecciones estudiadas en Java:

- `ArrayList`
- `HashMap`
- `HashSet`

Además, todas las colecciones utilizan genéricos para garantizar seguridad de tipos en tiempo de compilación.

La clase principal de esta funcionalidad es:

```text
CatalogoProductos
```

ubicada en:

```text
src/main/java/ec/edu/uees/proformas/servicio/CatalogoProductos.java
```

---

## Colecciones implementadas

El catálogo mantiene tres estructuras diferentes:

```java
private final List<Producto> productos;

private final Map<String, Producto> productosPorCodigo;

private final Set<String> codigosRegistrados;
```

Cada estructura cumple una responsabilidad específica.

### ArrayList

La lista de productos se implementa mediante:

```java
List<Producto> productos = new ArrayList<>();
```

Su función principal es mantener la colección de productos que puede ser recorrida y mostrada en la aplicación.

Se utiliza principalmente para:

- listar productos;
- mantener los objetos del catálogo;
- recorrer la colección;
- sincronizar la información mostrada en la interfaz.

---

## HashMap

Los productos también se almacenan en:

```java
Map<String, Producto> productosPorCodigo =
        new HashMap<>();
```

La clave es el código único del producto y el valor es el objeto `Producto`.

Ejemplo conceptual:

```text
P001 -> Laptop Pro
P002 -> Teclado Mecánico
P003 -> Curso Java
```

Esto permite realizar búsquedas por código sin recorrer toda la lista.

El método:

```java
public Producto buscarProducto(String codigo)
```

utiliza:

```java
return productosPorCodigo.get(codigo);
```

El `HashMap` es utilizado, por tanto, como índice para las búsquedas por código.

---

## HashSet

La colección:

```java
Set<String> codigosRegistrados =
        new HashSet<>();
```

almacena los códigos de los productos registrados.

Su objetivo es evitar duplicados.

Antes de agregar un producto se verifica:

```java
if (codigosRegistrados.contains(codigo)) {
    throw new IllegalArgumentException(
            "Ya existe un producto con el codigo "
            + codigo
    );
}
```

De esta forma no pueden existir dos productos con el mismo código dentro del catálogo.

---

## Genéricos

Las colecciones utilizan tipos genéricos:

```java
List<Producto>
Map<String, Producto>
Set<String>
```

Esto permite que Java verifique en tiempo de compilación los tipos almacenados.

Por ejemplo:

```java
List<Producto>
```

solo almacena objetos compatibles con `Producto`.

Mientras:

```java
Map<String, Producto>
```

establece que:

- las claves son `String`;
- los valores son objetos `Producto`.

---

## CRUD del catálogo

La clase `CatalogoProductos` implementa las operaciones requeridas sobre el catálogo.

### Crear

```java
agregarProducto(
        String codigo,
        Producto producto
)
```

Permite registrar un nuevo producto verificando previamente que el código no esté duplicado.

---

### Consultar

```java
buscarProducto(
        String codigo
)
```

Utiliza el `HashMap` para recuperar el producto asociado al código.

---

### Listar

```java
listarProductos()
```

Devuelve una copia de la colección:

```java
return new ArrayList<>(
        productos
);
```

Esto evita exponer directamente la colección interna del catálogo.

---

### Actualizar

```java
actualizarProducto(
        String codigo,
        Producto producto
)
```

Actualiza el producto tanto en la persistencia como en las estructuras de memoria.

---

### Eliminar

```java
eliminarProducto(
        String codigo
)
```

Elimina el producto de:

```text
SQLite
ArrayList
HashMap
HashSet
```

manteniendo sincronizadas las diferentes estructuras.

---

## Diseño de las colecciones

| Colección | Implementación | Uso |
|---|---|---|
| Lista | `ArrayList<Producto>` | Mantener y listar productos |
| Mapa | `HashMap<String, Producto>` | Buscar rápidamente por código |
| Conjunto | `HashSet<String>` | Evitar códigos duplicados |

El uso simultáneo de estas estructuras permite aplicar cada colección al problema para el cual resulta más adecuada.

---

# Semana 6 - Interfaz gráfica y manejo de eventos

Durante la Semana 6 el sistema dejó de utilizar únicamente interacción por consola y se incorporó una interfaz gráfica desarrollada con:

```text
JavaFX
```

La aplicación gráfica principal es:

```text
ec.edu.uees.proformas.ui.AplicacionJavaFX
```

---

## AplicacionJavaFX

La clase:

```java
public class AplicacionJavaFX extends Application
```

representa el punto de entrada de la interfaz gráfica.

JavaFX ejecuta:

```java
public void start(Stage escenario)
```

donde se construye la ventana principal.

La aplicación utiliza un:

```java
BorderPane
```

como contenedor general.

La ventana se divide conceptualmente en:

```text
TOP    -> encabezado
LEFT   -> menú de navegación
CENTER -> módulo activo
```

---

## Navegación

La aplicación incorpora tres módulos principales:

```text
Proformas
Productos
Clientes
```

mediante un menú lateral.

Cada botón cambia dinámicamente el contenido central de la aplicación.

Ejemplo:

```java
botonProductos.setOnAction(
        evento -> {
            raiz.setCenter(
                    vistaProductos
            );
        }
);
```

---

## Modelo evento - fuente - manejador

El manejo de eventos de JavaFX sigue el patrón:

```text
Fuente del evento
       ↓
Evento
       ↓
Manejador
       ↓
Acción
```

Por ejemplo:

```text
Botón Agregar
       ↓
clic del usuario
       ↓
setOnAction(...)
       ↓
agregarProducto()
```

La conexión entre el botón y el comportamiento se realiza mediante expresiones lambda.

```java
botonAgregar.setOnAction(
        evento -> agregarProducto()
);
```

---

# Vista de Productos

La clase:

```text
VistaProductos
```

implementa la interfaz gráfica del catálogo creado durante la Semana 5.

Utiliza controles JavaFX como:

- `TextField`
- `Button`
- `Label`
- `TableView`
- `TableColumn`
- `HBox`
- `VBox`

La interfaz permite realizar operaciones CRUD.

---

## Crear producto

El botón:

```text
Agregar
```

ejecuta:

```java
agregarProducto()
```

Se validan:

- código;
- nombre;
- precio;
- stock;
- campos vacíos;
- valores numéricos;
- códigos duplicados.

Después se delega la operación a:

```java
catalogo.agregarProducto(
        codigo,
        producto
);
```

---

## Consultar producto

El botón:

```text
Buscar
```

ejecuta:

```java
buscarProducto()
```

La búsqueda se realiza utilizando el código.

Cuando el producto existe, sus datos se cargan en los campos de la interfaz.

---

## Actualizar producto

El botón:

```text
Actualizar
```

ejecuta:

```java
actualizarProducto()
```

La interfaz captura los nuevos valores y delega la operación al catálogo.

---

## Eliminar producto

El botón:

```text
Eliminar
```

ejecuta:

```java
eliminarProducto()
```

Si el producto existe y puede ser eliminado respetando las reglas de integridad de la base de datos, la tabla visual se actualiza.

---

## Limpiar campos

También se incorporó:

```text
Limpiar
```

que permite borrar los campos de entrada y preparar la interfaz para una nueva operación.

---

## Tabla de productos

Los productos son mostrados mediante:

```java
TableView<Producto>
```

con columnas para:

```text
Nombre
Precio
Stock
```

Los datos visibles se mantienen mediante:

```java
ObservableList<Producto>
```

Cada vez que se modifica el catálogo se ejecuta:

```java
refrescarTabla();
```

---

# Validación y mensajes en la interfaz

La interfaz valida los datos antes de ejecutar cada operación.

Ejemplos de mensajes:

```text
Error: Complete todos los campos.
Error: Precio o stock no son validos.
Error: Producto no encontrado.
Estado: Producto agregado correctamente.
Estado: Producto actualizado correctamente.
Estado: Producto eliminado correctamente.
```

Las reglas propias del dominio continúan dentro de las clases del modelo.

Por ejemplo, `Producto` continúa siendo responsable de impedir:

- nombres vacíos;
- precios negativos;
- stock negativo.

La interfaz captura los errores y los comunica al usuario sin duplicar las reglas del dominio.

---

# Separación de responsabilidades

El proyecto utiliza una separación por paquetes.

```text
modelo
    ↓
reglas y entidades de negocio

servicio
    ↓
catálogos y administración de colecciones

persistencia
    ↓
acceso a SQLite

ui
    ↓
interfaz gráfica y eventos
```

La interfaz gráfica no realiza directamente las operaciones SQL.

Por ejemplo:

```text
VistaProductos
      ↓
CatalogoProductos
      ↓
RepositorioProductosSQLite
      ↓
SQLite
```

Esta separación reduce el acoplamiento entre presentación, lógica de aplicación y almacenamiento.

---

# Persistencia con SQLite

Como extensión del proyecto se incorporó persistencia utilizando:

```text
SQLite
```

y el controlador JDBC:

```text
org.xerial:sqlite-jdbc
```

La base de datos utilizada por la aplicación se encuentra localmente en:

```text
data/proformas.db
```

La carpeta `data` se crea automáticamente cuando la aplicación necesita conectarse.

---

## Base de datos local

La clase encargada de administrar la conexión es:

```text
ConexionSQLite
```

La URL JDBC utilizada es:

```java
jdbc:sqlite:data/proformas.db
```

La aplicación crea las tablas automáticamente mediante:

```java
ConexionSQLite.crearTablas();
```

---

# Modelo relacional

Actualmente la base de datos contiene cuatro tablas principales:

```text
clientes
productos
proformas
detalle_proforma
```

---

## Tabla productos

```text
productos
---------
codigo                 PRIMARY KEY
nombre
precio
stock
tipo
peso
ubicacion_almacen
tamano_mb
url_descarga
```

Permite almacenar tanto productos generales como información específica de productos físicos y digitales.

---

## Tabla clientes

```text
clientes
--------
email                  PRIMARY KEY
nombre
ciudad
tipo
```

El campo:

```text
tipo
```

permite identificar las diferentes implementaciones de cliente.

---

## Tabla proformas

```text
proformas
---------
id                     PRIMARY KEY AUTOINCREMENT
cliente_email           FOREIGN KEY
fecha
subtotal
descuento
total
```

La relación:

```text
cliente_email
```

referencia:

```text
clientes.email
```

---

## Tabla detalle_proforma

```text
detalle_proforma
----------------
id                     PRIMARY KEY AUTOINCREMENT
proforma_id            FOREIGN KEY
producto_codigo        FOREIGN KEY
cantidad
precio_unitario
subtotal
```

Las relaciones son:

```text
proforma_id
    -> proformas.id

producto_codigo
    -> productos.codigo
```

---

# Relaciones de la base de datos

El modelo relacional puede representarse de la siguiente forma:

```text
CLIENTES
   |
   | 1
   |
   | N
PROFORMAS
   |
   | 1
   |
   | N
DETALLE_PROFORMA
   |
   | N
   |
   | 1
PRODUCTOS
```

Es decir:

```text
clientes.email
       |
       v
proformas.cliente_email


proformas.id
       |
       v
detalle_proforma.proforma_id


productos.codigo
       |
       v
detalle_proforma.producto_codigo
```

---

# Integridad referencial

SQLite requiere activar explícitamente la validación de claves foráneas para cada conexión.

Por esta razón, la aplicación ejecuta:

```sql
PRAGMA foreign_keys = ON;
```

La clase `ConexionSQLite` también comprueba que la configuración haya sido activada correctamente mediante:

```sql
PRAGMA foreign_keys;
```

Si SQLite no devuelve:

```text
1
```

la conexión se considera inválida y se lanza una excepción.

Esto evita operar accidentalmente con las claves foráneas desactivadas.

---

# Transacciones

El guardado de una proforma utiliza una transacción.

Una proforma está formada por:

```text
cabecera
+
uno o varios detalles
```

La cabecera se almacena en:

```text
proformas
```

y sus productos en:

```text
detalle_proforma
```

Antes de iniciar la operación se desactiva temporalmente el autocommit:

```java
conexion.setAutoCommit(false);
```

Si toda la operación finaliza correctamente:

```java
conexion.commit();
```

Si ocurre cualquier error:

```java
conexion.rollback();
```

De esta forma no puede quedar almacenada una cabecera sin sus detalles correspondientes debido a una operación incompleta.

---

# PreparedStatement

Los repositorios utilizan:

```java
PreparedStatement
```

para parametrizar las operaciones SQL.

Ejemplo conceptual:

```sql
INSERT INTO productos (...)
VALUES (?, ?, ?, ...)
```

Esta estrategia mantiene separados los datos de la sentencia SQL y simplifica el manejo de parámetros.

---

# Repositorios SQLite

La capa de persistencia contiene:

```text
ConexionSQLite
RepositorioProductosSQLite
RepositorioClientesSQLite
RepositorioProformasSQLite
```

### RepositorioProductosSQLite

Administra la persistencia del catálogo de productos.

### RepositorioClientesSQLite

Administra los clientes registrados.

### RepositorioProformasSQLite

Administra:

- cabecera de proforma;
- detalles de la proforma;
- transacciones;
- consulta de proformas guardadas;
- consulta de detalles.

---

# Gestión de proformas

Además del CRUD de productos requerido para las Semanas 5 y 6, el proyecto integra el módulo de proformas desarrollado durante las semanas anteriores.

Una proforma:

```text
Proforma
   |
   +-- Cliente
   |
   +-- List<ItemProforma>
```

Cada `ItemProforma` almacena:

```text
código del producto
producto
cantidad
subtotal
```

El subtotal del ítem se calcula mediante:

```java
producto.getPrecio() * cantidad
```

---

## Cálculo de la proforma

La clase `Proforma` calcula:

```text
Subtotal
Descuento
Total
```

El subtotal corresponde a la suma de todos los ítems.

El descuento depende del tipo real de cliente mediante:

```java
cliente.calcularDescuento()
```

Finalmente:

```java
total = subtotal * (1 - descuento)
```

---

# Interfaz de Proformas

La clase:

```text
VistaProformas
```

permite trabajar gráficamente con:

- búsqueda de clientes;
- selección de productos;
- cantidades;
- cálculo de subtotales;
- descuentos;
- total;
- almacenamiento de la proforma;
- consulta de proformas guardadas;
- consulta de detalles.

Esto reutiliza las clases construidas durante las semanas anteriores dentro de la interfaz gráfica actual.

---

# Interfaz de Clientes

La clase:

```text
VistaClientes
```

permite administrar los clientes desde la interfaz gráfica.

Los datos son gestionados mediante:

```text
VistaClientes
      ↓
CatalogoClientes
      ↓
RepositorioClientesSQLite
      ↓
SQLite
```

---

# Arquitectura actual

La arquitectura actual puede representarse de la siguiente forma:

```text
┌──────────────────────────────────────────┐
│                 JavaFX                   │
│                                          │
│  VistaProductos                          │
│  VistaClientes                           │
│  VistaProformas                          │
└────────────────────┬─────────────────────┘
                     │
                     v
┌──────────────────────────────────────────┐
│              Servicios                   │
│                                          │
│  CatalogoProductos                       │
│  CatalogoClientes                        │
└────────────────────┬─────────────────────┘
                     │
                     v
┌──────────────────────────────────────────┐
│              Persistencia                │
│                                          │
│  RepositorioProductosSQLite              │
│  RepositorioClientesSQLite               │
│  RepositorioProformasSQLite              │
│  ConexionSQLite                          │
└────────────────────┬─────────────────────┘
                     │
                     v
┌──────────────────────────────────────────┐
│                SQLite                    │
│                                          │
│ productos                                │
│ clientes                                 │
│ proformas                                │
│ detalle_proforma                         │
└──────────────────────────────────────────┘
```

Las clases del paquete `modelo` son utilizadas transversalmente por las diferentes capas.

---

# Estructura actual del proyecto

```text
uees-oop-java/
│
├── .gitignore
├── pom.xml
├── README.md
│
├── data/
│   └── proformas.db
│       [generado localmente - no versionado]
│
├── docs/
│   ├── uml/
│   └── evidencias/
│
└── src/
    └── main/
        └── java/
            └── ec/
                └── edu/
                    └── uees/
                        └── proformas/
                            │
                            ├── Proformas.java
                            │
                            ├── excepciones/
                            │   └── StockInsuficienteException.java
                            │
                            ├── modelo/
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
                            ├── servicio/
                            │   ├── CatalogoProductos.java
                            │   └── CatalogoClientes.java
                            │
                            ├── persistencia/
                            │   ├── ConexionSQLite.java
                            │   ├── RepositorioProductosSQLite.java
                            │   ├── RepositorioClientesSQLite.java
                            │   └── RepositorioProformasSQLite.java
                            │
                            └── ui/
                                ├── AplicacionJavaFX.java
                                ├── VistaProductos.java
                                ├── VistaClientes.java
                                └── VistaProformas.java
```

---

# Clases principales

## Modelo

- `Cliente`
- `ClienteMayorista`
- `ClienteMinorista`
- `ClienteCorporativo`
- `Producto`
- `ProductoFisico`
- `ProductoDigital`
- `ItemProforma`
- `Proforma`
- `ProformaResumen`
- `DetalleProformaResumen`

## Servicios

- `CatalogoProductos`
- `CatalogoClientes`

## Persistencia

- `ConexionSQLite`
- `RepositorioProductosSQLite`
- `RepositorioClientesSQLite`
- `RepositorioProformasSQLite`

## Interfaz gráfica

- `AplicacionJavaFX`
- `VistaProductos`
- `VistaClientes`
- `VistaProformas`

## Excepciones

- `StockInsuficienteException`

---

# Modelo general del dominio

```text
                    Cliente
                   <<abstract>>
              /         |          \
             /          |           \
            v           v            v
 ClienteMayorista ClienteMinorista ClienteCorporativo
             \          |           /
              \         |          /
               +--------+---------+
                        |
                        v
                     Proforma
                        |
                        v
                List<ItemProforma>
                        |
                        v
                     Producto
                    /        \
                   /          \
                  v            v
         ProductoFisico   ProductoDigital
```

---

# Diagramas UML

## Semana 1

![UML Semana 1](docs/uml/producto_cliente.png)

## Semana 2

![UML Semana 2](docs/uml/uml_semana2_herencia_composicion.drawio.png)

## Semana 3

El diagrama de Semana 3 incorpora:

- clase abstracta `Cliente`
- método abstracto `calcularDescuento()`
- `ClienteMayorista`
- `ClienteMinorista`
- generalización
- sobrescritura
- polimorfismo
- relaciones con `Proforma`
- composición con `ItemProforma`
- jerarquía de productos

![UML Semana 3](docs/uml/uml_semana3_polimorfismo.png)

El archivo editable de diagrams.net también se encuentra en:

```text
docs/uml/uml_semana3_polimorfismo.drawio
```

---

# Evidencias - Semana 3 Java

## 1. Cliente como clase abstracta

Declaración de `Cliente` como clase abstracta:

![Cliente abstracto](docs/evidencias/semana3/java/windows/01a_cliente_clase_abstracta_netbeans.png)

Método abstracto `calcularDescuento()`:

![Método abstracto](docs/evidencias/semana3/java/windows/01b_cliente_metodo_abstracto_netbeans.png)

## 2. ClienteMayorista

Implementación de `ClienteMayorista` con descuento del 20 %:

![ClienteMayorista](docs/evidencias/semana3/java/windows/02_cliente_mayorista_netbeans.png)

## 3. ClienteMinorista

Implementación de `ClienteMinorista` con descuento del 5 %:

![ClienteMinorista](docs/evidencias/semana3/java/windows/03_cliente_minorista_netbeans.png)

## 4. Polimorfismo

Uso del método `calcularDescuento()` a través de la abstracción `Cliente`:

![Polimorfismo Java](docs/evidencias/semana3/java/windows/04_polimorfismo_netbeans.png)

## 5. Prueba en Windows PowerShell

Compilación y ejecución independiente del proyecto utilizando `javac` y `java`:

![Prueba Java Windows](docs/evidencias/semana3/java/windows/05_prueba_powershell_windows.png)

## 6. Entorno Java en Fedora

Verificación de la rama Git y del entorno Java/OpenJDK utilizado en Fedora Linux:

![Entorno Fedora Java](docs/evidencias/semana3/java/fedora/06_fedora_entorno_y_branch_java.png)

## 7. Prueba en Fedora Linux

Compilación y ejecución completa del proyecto en Fedora:

![Prueba Java Fedora](docs/evidencias/semana3/java/fedora/07_prueba_java_fedora.png)

Los resultados obtenidos en Windows y Fedora son equivalentes, demostrando la portabilidad de la implementación Java.

---

# Evidencias - Semanas 5 y 6

Para la entrega de las Semanas 5 y 6 se validó el funcionamiento de:

```text
Colecciones
Genéricos
CRUD de productos
JavaFX
Manejo de eventos
Validaciones
SQLite
Persistencia
Claves foráneas
Transacciones
Proformas
```

Las capturas completas del funcionamiento de la aplicación se incorporarán en el documento PDF correspondiente a la entrega académica.

---

# Requisitos

El proyecto utiliza Maven para administrar las dependencias.

## Java

El proyecto está configurado actualmente para:

```text
Java 25
```

Verificar con:

```powershell
java -version
```

---

## Maven

Verificar Maven mediante:

```powershell
mvn -version
```

---

## Dependencias principales

Maven descarga automáticamente:

```text
JavaFX Controls 25.0.1
SQLite JDBC 3.53.4.0
```

Por tanto, no es necesario descargar manualmente los archivos JAR correspondientes.

---

# Descargar el proyecto

El repositorio público es:

```text
https://github.com/sergiomendez2026/uees-oop-java
```

Actualmente la implementación correspondiente a las Semanas 5 y 6 se encuentra en:

```text
fase-03-interfaz-java
```

Clonar:

```bash
git clone https://github.com/sergiomendez2026/uees-oop-java.git
```

Ingresar al proyecto:

```bash
cd uees-oop-java
```

Seleccionar la rama:

```bash
git checkout fase-03-interfaz-java
```

---

# Compilar el proyecto

Desde la raíz del repositorio:

```bash
mvn clean package
```

Si la compilación es correcta Maven debe finalizar con:

```text
BUILD SUCCESS
```

---

# Ejecutar la aplicación gráfica

La clase principal de JavaFX es:

```text
ec.edu.uees.proformas.ui.AplicacionJavaFX
```

Ejecutar:

```bash
mvn javafx:run
```

También puede limpiarse, compilarse y ejecutarse mediante:

```bash
mvn clean javafx:run
```

---

# Ejecución desde Apache NetBeans

1. Abrir Apache NetBeans.
2. Seleccionar `File`.
3. Seleccionar `Open Project`.
4. Abrir la carpeta del proyecto `proformas`.
5. Esperar a que Maven descargue las dependencias.
6. Ejecutar `Clean and Build`.
7. Abrir una terminal en la raíz del proyecto.
8. Ejecutar:

```bash
mvn javafx:run
```

La ventana:

```text
Sistema de Gestion de Proformas
```

debe aparecer en pantalla.

---

# Primera ejecución

Durante la primera ejecución se crea automáticamente:

```text
data/
```

y posteriormente:

```text
data/proformas.db
```

También se crean automáticamente las tablas:

```text
productos
clientes
proformas
detalle_proforma
```

No es necesario crear manualmente la base de datos.

---

# Archivo SQLite y Git

El archivo:

```text
data/proformas.db
```

contiene datos locales de ejecución y no forma parte del código fuente.

Por esta razón está excluido mediante:

```text
data/*.db
```

en `.gitignore`.

Cada persona que clone el repositorio puede generar su propia base de datos al ejecutar la aplicación.

---

# Prueba básica de funcionamiento

Una prueba manual puede realizarse siguiendo este flujo:

```text
1. Ejecutar la aplicación.

2. Abrir Productos.

3. Registrar un producto:
   código: P001
   nombre: Laptop Pro
   precio: 900
   stock: 5

4. Registrar otro producto:
   código: P002
   nombre: Teclado Mecanico RGB
   precio: 89.99
   stock: 15

5. Buscar los productos por código.

6. Actualizar alguno de sus datos.

7. Verificar que no pueda registrarse nuevamente
   un código ya existente.

8. Abrir Clientes.

9. Registrar o consultar un cliente.

10. Abrir Proformas.

11. Buscar el cliente.

12. Agregar productos y cantidades.

13. Verificar subtotal, descuento y total.

14. Guardar la proforma.

15. Consultar las proformas guardadas.

16. Cerrar y volver a ejecutar la aplicación.

17. Verificar que los datos continúen almacenados.
```

---

# Ejecución histórica por consola - Semanas 1 a 4

Las versiones anteriores del proyecto también pueden ejecutarse desde la clase:

```text
ec.edu.uees.proformas.Proformas
```

Esta clase conserva diferentes pruebas realizadas durante la evolución académica del proyecto.

La interfaz gráfica actual utiliza:

```text
ec.edu.uees.proformas.ui.AplicacionJavaFX
```

como punto de entrada.

---

# Ejemplo de ejecución Semana 3

```text
Descuento cliente mayorista: 20.0%
Descuento cliente minorista: 5.0%

Producto fisico: Laptop
Peso: 2.1 kg
Ubicacion: Bodega A

Producto digital: Curso Java
Tamano: 1500.0 MB
URL: https://ejemplo.com/curso

=== PROFORMA CLIENTE MAYORISTA ===
Cliente: Sergio
Descuento: 20.0%
Subtotal Laptop: $850.0
Subtotal Curso: $240.0
Total Proforma: $872.0

=== PROFORMA CLIENTE MINORISTA ===
Cliente: Ana
Descuento: 5.0%
Subtotal Laptop: $850.0
Subtotal Curso: $240.0
Total Proforma: $1035.5
```

---

# Tecnologías utilizadas

- Java
- JavaFX
- Maven
- JDBC
- SQLite
- Apache NetBeans
- DBeaver
- Windows PowerShell
- Fedora Linux
- UML
- diagrams.net
- Git
- GitHub

---

# Conceptos aplicados

A lo largo de la evolución del proyecto se han aplicado:

### Programación orientada a objetos

- Clases y objetos
- Encapsulación
- Asociación
- Herencia
- Composición
- Abstracción
- Clases abstractas
- Métodos abstractos
- Sobrescritura
- Polimorfismo
- Enlace dinámico

### Validaciones y excepciones

- Validación mediante setters
- `IllegalArgumentException`
- Excepción personalizada
- `StockInsuficienteException`
- Propagación de excepciones

### Colecciones

- `List`
- `ArrayList`
- `Map`
- `HashMap`
- `Set`
- `HashSet`

### Genéricos

- `List<Producto>`
- `Map<String, Producto>`
- `Set<String>`

### Interfaz gráfica

- JavaFX
- `Application`
- `Stage`
- `Scene`
- `BorderPane`
- `VBox`
- `HBox`
- `Button`
- `TextField`
- `Label`
- `TableView`
- `ObservableList`

### Manejo de eventos

- programación orientada a eventos;
- fuentes de eventos;
- manejadores;
- `setOnAction`;
- expresiones lambda.

### Persistencia

- JDBC
- SQLite
- SQL
- `PreparedStatement`
- claves primarias
- claves foráneas
- restricciones `CHECK`
- transacciones
- `commit`
- `rollback`
- integridad referencial

---

# Estado actual del proyecto

La implementación disponible en:

```text
fase-03-interfaz-java
```

integra el trabajo desarrollado desde la Semana 1 hasta la Semana 6.

La versión actual contiene:

```text
Modelo orientado a objetos
        +
Excepciones y validaciones
        +
Colecciones y genéricos
        +
CRUD de productos
        +
Interfaz JavaFX
        +
Manejo de eventos
        +
SQLite
        +
Persistencia
        +
Clientes
        +
Proformas
```

El proyecto continuará evolucionando conforme se incorporen nuevas actividades de la asignatura.

---

# Autor

Sergio Méndez
