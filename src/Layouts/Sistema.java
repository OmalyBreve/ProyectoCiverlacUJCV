package Layouts;

import Cliente.DatosCliente;
import Cliente.datosProductoCompra;
import civerlac.Cliente;
import civerlac.ClienteDB;
import civerlac.ComprasDB;
import civerlac.ConexionSQL;
import civerlac.Empleado;
import civerlac.EmpleadosDB;
import civerlac.PrecioHistorico;
import civerlac.ProductoDB;
import civerlac.ProveedorDB;
import civerlac.VentaDB;
import civerlac.Ventas;
import civerlac.VentasDB;
import civerlac.VentasFacturacionHistorial;
import civerlac.producto;
import civerlac.valoresEstaticos;
import java.awt.Color;
import static java.awt.PageAttributes.MediaType.A;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Omaly Breve
 */
public class Sistema extends javax.swing.JFrame {

    /**
     * Creates new form Sistema
     */
    public Sistema() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/LOGO.png")).getImage());
        this.setTitle("Sistema Civerlac");
        this.setLocationRelativeTo(null);
        ///
        txtIdClienteCLI.setText("Automatico");
        txtIdProductosProductos.setText("#Auto");
        txtIdClienteCLI.setEnabled(false);

        ///
        listaClientes();
        listaProveedores();
        listaProductos();
        listaEmpleados();
        listaHistorialVenta();
        listaHistorialVentaFacutra();
        listaProductoCustom();
        cargarMeses();
        btncancel.setVisible(false);
        btncanelProveedor.setVisible(false);
        btnCancelEmp.setVisible(false);

        //
        lblTotal.setText("0.00");
        lblcompraTotal.setText("0.00");
        //

        System.out.println("privilegio de usuario:" + PrivilegioUser);
        if (PrivilegioUser != 2) {
            BloquearAcceso();
        }

        UsuarioAcceso();
        panelInfo.setSelectedIndex(6);
    }

    String estado = "";
    //****MEDOTOS CLIENTES****
    Object[] meses = new Object[12];
    Cliente cl = new Cliente();
    ClienteDB cliente = new ClienteDB();
    valoresEstaticos vals = new valoresEstaticos();
    EmpleadosDB empleadodb = new EmpleadosDB();
    DefaultTableModel modeloTB = new DefaultTableModel();
    DefaultTableModel modelProveedor = new DefaultTableModel();
    DefaultTableModel modelEmpleado = new DefaultTableModel();
    DefaultTableModel modelProducto = new DefaultTableModel();
    DefaultTableModel modelVenta = new DefaultTableModel();
    DefaultTableModel modelHistorialVenta = new DefaultTableModel();
    DefaultTableModel modelHistorialVentaFactura = new DefaultTableModel();
    DefaultTableModel modeloVacio = new DefaultTableModel();
    DefaultTableModel modelPHproducto = new DefaultTableModel();
    DefaultTableModel modelListaPrecio = new DefaultTableModel();
    DefaultTableModel modelListaPrecioMovimiento = new DefaultTableModel();
    DefaultTableModel modeloCompra = new DefaultTableModel();

    /**
     *
     */
    public void BloquearAcceso() {
        panelInfo.setEnabledAt(0, false);
        panelInfo.setEnabledAt(1, false);
        panelInfo.setEnabledAt(2, false);
        panelInfo.setEnabledAt(3, false);
        panelInfo.setEnabledAt(4, false);
        panelInfo.setEnabledAt(panelInfo.getTabCount() - 1, false);
        panelInfo.setSelectedIndex(6);
        //bloquear botones
        btnClientes.setEnabled(false);
        btnProveedores.setEnabled(false);
        btnEmpleados.setEnabled(false);
        btnProductos.setEnabled(false);
        jButton4.setEnabled(false);
        btnVentas.setEnabled(false);
        btnEmpresa.setEnabled(false);

    }

    public void UsuarioAcceso() {
        valoresEstaticos vals = new valoresEstaticos();
        int idUser = vals.getIdUsuario();
        System.out.println("el usuario es :" + idUser);
        ConexionSQL cn = new ConexionSQL();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql2 = "SELECT * FROM usuariopermiso where idUsuario = ? ";
            con = cn.getConnection();
            ps = con.prepareStatement(sql2);
            ps.setInt(1, vals.getIdUsuario());
            rs = ps.executeQuery();
            if (rs.next()) {

                con = cn.getConnection();
                ps = con.prepareStatement(sql2);
                ps.setInt(1, idUser);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println(rs.getString("Clientes"));
                    if ("24".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnModificarCLI.setEnabled(false);
                        ClienteBuscar.setEnabled(false);
                    } else if ("27".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(false);
                        btnModificarCLI.setEnabled(true);
                        ClienteBuscar.setEnabled(false);

                    } else if ("2".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(false);
                        btnModificarCLI.setEnabled(false);
                        ClienteBuscar.setEnabled(false);

                    } else if ("0".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, false);
                        btnClientes.setEnabled(false);

                    } else if ("2479".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(true);
                        btnModificarCLI.setEnabled(true);
                        ClienteBuscar.setEnabled(true);

                    } else if ("249".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(true);
                        btnModificarCLI.setEnabled(false);
                        ClienteBuscar.setEnabled(true);

                    } else if ("247".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(true);
                        btnModificarCLI.setEnabled(true);
                        ClienteBuscar.setEnabled(false);

                    } else if ("29".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(false);
                        btnModificarCLI.setEnabled(false);
                        ClienteBuscar.setEnabled(true);

                    } else if ("27".equals(rs.getString("Clientes"))) {
                        panelInfo.setEnabledAt(0, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoClienteCLI.setEnabled(false);
                        btnModificarCLI.setEnabled(true);
                        ClienteBuscar.setEnabled(false);
                    }
                    /// proveedor
                    if ("24".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnModificarProveedores.setEnabled(false);
                        jButton2.setEnabled(false);
                    } else if ("27".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnModificarProveedores.setEnabled(true);
                        jButton2.setEnabled(false);

                    } else if ("2".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnNuevoProveedores.setEnabled(false);
                        btnModificarProveedores.setEnabled(false);
                        jButton2.setEnabled(false);

                    } else if ("0".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, false);
                        btnProveedores.setEnabled(false);

                    } else if ("2479".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnNuevoProveedores.setEnabled(true);
                        btnModificarProveedores.setEnabled(true);
                        jButton2.setEnabled(true);

                    } else if ("249".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnNuevoProveedores.setEnabled(true);
                        btnModificarProveedores.setEnabled(false);
                        jButton2.setEnabled(true);

                    } else if ("247".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnNuevoProveedores.setEnabled(true);
                        btnModificarProveedores.setEnabled(true);
                        jButton2.setEnabled(false);

                    } else if ("29".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnProveedores.setEnabled(true);
                        btnNuevoProveedores.setEnabled(false);
                        btnModificarProveedores.setEnabled(false);
                        jButton2.setEnabled(true);

                    } else if ("27".equals(rs.getString("Proveedores"))) {
                        panelInfo.setEnabledAt(1, true);
                        //bloquear botones
                        btnClientes.setEnabled(true);
                        btnNuevoProveedores.setEnabled(false);
                        btnModificarProveedores.setEnabled(true);
                        jButton2.setEnabled(false);
                    }
                    //Empleados

                    System.out.println(rs.getString("Empleados"));
                    String codEmpleado = "";

                    if (rs.getString("Empleados").contains("3")) {
                        codEmpleado = rs.getString("Empleados").substring(0, rs.getString("Empleados").length() - 1);
                    } else {
                        codEmpleado = rs.getString("Empleados");
                    }
                    System.out.println("el codigo de empleado es:" + codEmpleado);
                    if ("24".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                        btnEmpleados.setEnabled(true);
                        btnModificarEmpleado.setEnabled(false);
                        jButton3.setEnabled(false);

                    } else if ("2".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                        btnEmpleados.setEnabled(true);
                        btnModificarEmpleado.setEnabled(false);
                        jButton3.setEnabled(false);
                        btnNuevoEmpleado.setEnabled(false);
                    } else if ("0".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, false);
                        //bloquear botones
                        btnEmpleados.setEnabled(false);
                        btnModificarEmpleado.setEnabled(false);
                        jButton3.setEnabled(false);
                        btnNuevoEmpleado.setEnabled(false);
                    } else if ("2479".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                    } else if ("249".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                        btnEmpleados.setEnabled(true);
                        btnModificarEmpleado.setEnabled(false);

                    } else if ("247".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                        btnEmpleados.setEnabled(true);

                        jButton3.setEnabled(false);

                    } else if ("29".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                        btnEmpleados.setEnabled(true);
                        btnModificarEmpleado.setEnabled(false);
                        jButton3.setEnabled(true);
                        btnNuevoEmpleado.setEnabled(false);
                    } else if ("27".equals(codEmpleado)) {
                        panelInfo.setEnabledAt(2, true);
                        //bloquear botones
                        btnEmpleados.setEnabled(true);
                        btnModificarEmpleado.setEnabled(true);
                        jButton3.setEnabled(false);
                        btnNuevoEmpleado.setEnabled(false);

                    }
                    if (!rs.getString("Empleados").contains("3")) {
                        btnAddUser.setEnabled(false);
                    }
                    //Productos

                    System.out.println(rs.getString("Productos"));
                    if ("24".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnProdModificar.setEnabled(false);
                        btnEliminarProductos.setEnabled(false);

                    } else if ("27".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnProdModificar.setEnabled(true);
                        btnEliminarProductos.setEnabled(false);
                        btnInsert.setEnabled(false);

                    } else if ("2".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnProdModificar.setEnabled(false);
                        btnInsert.setEnabled(false);
                        btnEliminarProductos.setEnabled(false);

                    } else if ("0".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, false);
                        //bloquear botones
                        btnProductos.setEnabled(false);
                        btnProdModificar.setEnabled(false);
                        btnInsert.setEnabled(false);
                        btnEliminarProductos.setEnabled(false);

                    } else if ("2479".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnProdModificar.setEnabled(true);
                        btnInsert.setEnabled(true);
                        btnEliminarProductos.setEnabled(true);

                    } else if ("249".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnInsert.setEnabled(true);
                        btnProdModificar.setEnabled(false);
                        btnEliminarProductos.setEnabled(true);

                    } else if ("247".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnInsert.setEnabled(true);
                        btnProdModificar.setEnabled(true);
                        btnEliminarProductos.setEnabled(false);

                    } else if ("29".equals(rs.getString("Productos"))) {
                        panelInfo.setEnabledAt(3, true);
                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnInsert.setEnabled(false);
                        btnProdModificar.setEnabled(false);
                        btnEliminarProductos.setEnabled(true);

                    } else if ("27".equals(rs.getString("Productos"))) {

                        //bloquear botones
                        btnProductos.setEnabled(true);
                        btnInsert.setEnabled(false);
                        btnProdModificar.setEnabled(true);
                        btnEliminarProductos.setEnabled(false);
                    }
                    ///CompraVenta
                    System.out.println(rs.getString("CompraVenta"));
                    if ("24".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(5, false);
                        panelInfo.setEnabledAt(7, false);
                        //bloquear botones
                        jButton1.setEnabled(false);
                        jButton5.setEnabled(false);
                    } else if ("2".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(5, false);
                        panelInfo.setEnabledAt(7, false);
                        panelInfo.setEnabledAt(6, true);
                        //bloquear botones
                        jButton1.setEnabled(false);
                        jButton5.setEnabled(false);
                        btnNuevaVenta.setEnabled(true);

                    } else if ("0".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(5, false);
                        panelInfo.setEnabledAt(7, false);
                        panelInfo.setEnabledAt(6, false);
                        panelInfo.setEnabledAt(4, false);
                        //
                        jButton1.setEnabled(false);
                        jButton5.setEnabled(false);
                        btnVentas.setEnabled(false);
                        btnNuevaVenta.setEnabled(false);

                    } else if ("2479".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(5, true);
                        panelInfo.setEnabledAt(7, true);
                        panelInfo.setEnabledAt(6, true);
                        panelInfo.setEnabledAt(4, true);
//
                        jButton1.setEnabled(true);
                        jButton5.setEnabled(true);
                        btnVentas.setEnabled(true);
                        btnNuevaVenta.setEnabled(true);

                    } else if ("249".equals(rs.getString("CompraVenta"))) {
                        jButton1.setEnabled(true);
                    } else if ("247".equals(rs.getString("CompraVenta"))) {
                        jButton5.setEnabled(false);
                         panelInfo.setEnabledAt(5, true);
                    } else if ("29".equals(rs.getString("CompraVenta"))) {
                       panelInfo.setEnabledAt(4, true);
                        panelInfo.setEnabledAt(5, true);
                        btnVentas.setEnabled(false);
                        jButton1.setEnabled(false);
                    } else if ("27".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(4, true);
                        panelInfo.setEnabledAt(5, true);
                        
                        btnVentas.setEnabled(false);
                        jButton5.setEnabled(false);

                    } else if ("47".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(6, false);
                        panelInfo.setEnabledAt(5, false);
                        
                        btnNuevaVenta.setEnabled(false);
                        jButton5.setEnabled(false);

                    } else if ("249".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(4, true);
                        btnVentas.setEnabled(false);

                    } else if ("7".equals(rs.getString("CompraVenta"))) {
                        panelInfo.setEnabledAt(7, false);
                        panelInfo.setEnabledAt(4, false);
                         panelInfo.setEnabledAt(5, false);
                        
                        btnNuevaVenta.setEnabled(false);
                        btnVentas.setEnabled(false);
                        jButton5.setEnabled(false);

                    }

                    //otros
                    if ("24".equals(rs.getString("Otros"))) {
                        btnEmpresa.setEnabled(true);
                        panelInfo.setEnabledAt(8, true);
                    } else if ("4".equals(rs.getString("Otros"))) {
                        btnEmpresa.setEnabled(false);
                        panelInfo.setEnabledAt(8, true);
                    } else if ("2".equals(rs.getString("Otros"))) {
                        panelInfo.setEnabledAt(8, false);
                        btnEmpresa.setEnabled(true);
                    } else if ("0".equals(rs.getString("Otros"))) {
                        panelInfo.setEnabledAt(8, false);
                        btnEmpresa.setEnabled(false);
                    }

                }

            } else {
                JOptionPane.showMessageDialog(null, "El usuario no esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }

    }

    public static List<datosProductoCompra> getListaProd() {
        return listaProd;
    }

    public static void setListaProd(List<datosProductoCompra> listaProd) {
        Sistema.listaProd = listaProd;
    }

    public static List<datosProductoCompra> listaProd = new ArrayList();

    public void listaClientes() {
        List<Cliente> listaCl = cliente.listarClientes();
        modeloTB = (DefaultTableModel) tbClientesCLI.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getIdCliente();
            obj[1] = listaCl.get(i).getNombre();
            obj[2] = listaCl.get(i).getCorreo();
            obj[3] = listaCl.get(i).getDireccion();
            obj[4] = listaCl.get(i).getIdTelefonoCli();
            if (listaCl.get(i).getEstado() == 1) {
                obj[5] = "Habilitado";
            } else {
                obj[5] = "Inhabilitado";
            }

            modeloTB.addRow(obj);

        }
        tbClientesCLI.setModel(modeloTB);
    }

    public void listaProveedores() {
        ProveedorDB cliente = new ProveedorDB();
        List<Cliente> listaCl = cliente.listarClientes();
        modelProveedor = (DefaultTableModel) tbProveedores.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getIdCliente();
            obj[1] = listaCl.get(i).getNombre();
            obj[2] = listaCl.get(i).getCorreo();
            obj[3] = listaCl.get(i).getIdTelefonoCli();
            obj[4] = listaCl.get(i).getDireccion();
            if (listaCl.get(i).getEstado() == 1) {
                obj[5] = "Habilitado";
            } else {
                obj[5] = "Inhabilitado";
            }

            modelProveedor.addRow(obj);

        }
        tbProveedores.setModel(modelProveedor);
    }

    public void listaProductos() {
        ProductoDB cliente = new ProductoDB();
        List<producto> listaCl = cliente.listarProductos();
        modelProducto = (DefaultTableModel) tbProductos.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getId();
            obj[1] = listaCl.get(i).getNombre();
            obj[2] = listaCl.get(i).getPreciol();
            obj[3] = listaCl.get(i).getStock();

            modelProducto.addRow(obj);

        }
        tbProductos.setModel(modelProducto);
    }

    public void listaProductoCustom() {
        ProductoDB cliente = new ProductoDB();
        List<producto> listaCl = cliente.listarProductos();
        modelPHproducto = (DefaultTableModel) tbPHproducto.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getId();
            obj[1] = listaCl.get(i).getNombre();
            obj[2] = listaCl.get(i).getPreciol();

            modelPHproducto.addRow(obj);

        }
        tbPHproducto.setModel(modelPHproducto);
    }

    public void listarPrecios(int id) {
        DateFormat f = new SimpleDateFormat("yyy-MM");
        ProductoDB cliente = new ProductoDB();
        List<PrecioHistorico> listaCl = cliente.listarPrecioHistorico(id);
        modelListaPrecio = (DefaultTableModel) tbPHhistorial.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getFecha();

            modelListaPrecio.addRow(obj);

        }
        tbPHhistorial.setModel(modelListaPrecio);
    }

    public void listarPreciosMovimiento(String date, int id) {
        DateFormat f = new SimpleDateFormat("yyy-MM");
        ProductoDB cliente = new ProductoDB();
        List<PrecioHistorico> listaCl = cliente.listarPrecioHistorico2(date, id);
        modelListaPrecioMovimiento = (DefaultTableModel) tbPHhistorialprecio.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getPrecio();

            modelListaPrecioMovimiento.addRow(obj);

        }
        tbPHhistorialprecio.setModel(modelListaPrecioMovimiento);
    }

    public void listaEmpleados() {
        EmpleadosDB cliente = new EmpleadosDB();
        List<Empleado> listaCl = cliente.ListEmpleados();
        modelEmpleado = (DefaultTableModel) tbEmpleados.getModel();

        Object[] obj = new Object[7];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getIdEmpleado();
            obj[1] = listaCl.get(i).getNombre();
            obj[2] = listaCl.get(i).getOcupacion();
            obj[3] = listaCl.get(i).getCorreo();
            obj[4] = listaCl.get(i).getDireccion();
            obj[5] = listaCl.get(i).getTelefono();
            if (listaCl.get(i).getEstado() == 1) {
                obj[6] = "Habilitado";
            } else {
                obj[6] = "Inhabilitado";
            }

            modelEmpleado.addRow(obj);

        }
        tbEmpleados.setModel(modelEmpleado);
    }

    public void listaHistorialVenta() {
        VentasDB cliente = new VentasDB();
        List<Ventas> listaCl = cliente.ListarHistorialVenta();
        modelHistorialVenta = (DefaultTableModel) tbVentas.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getIdFactura();
            obj[1] = listaCl.get(i).getCliente();
            obj[2] = listaCl.get(i).getEmpleado();
            obj[3] = listaCl.get(i).getTotal();
            modelHistorialVenta.addRow(obj);

        }
        tbVentas.setModel(modelHistorialVenta);
    }

    public void listaHistorialVentaCustom(String filtro) {
        VentasDB cliente = new VentasDB();
        List<Ventas> listaCl = cliente.ListarHistorialVentaCustom(filtro);
        modelHistorialVenta = (DefaultTableModel) tbVentas.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getIdFactura();
            obj[1] = listaCl.get(i).getCliente();
            obj[2] = listaCl.get(i).getEmpleado();
            obj[3] = listaCl.get(i).getTotal();
            modelHistorialVenta.addRow(obj);

        }
        tbVentas.setModel(modelHistorialVenta);
    }
    //

    public void listaHistorialVentaFacutra() {
        VentasDB cliente = new VentasDB();
        List<VentasFacturacionHistorial> listaCl = cliente.ListarHistorialVentaFactura();
        modelHistorialVentaFactura = (DefaultTableModel) tbhistorialventaFactura.getModel();

        Object[] obj = new Object[6];
        for (int i = 0; i < listaCl.size(); i++) {
            obj[0] = listaCl.get(i).getIdFactura();
            obj[1] = listaCl.get(i).getFecha();
            obj[2] = listaCl.get(i).getEmpleado();
            obj[3] = listaCl.get(i).getCliente();

            modelHistorialVentaFactura.addRow(obj);

        }
        tbhistorialventaFactura.setModel(modelHistorialVentaFactura);
    }

    public void listaHistorialVentaFacutrafecha() {

    }
    //Limpiar tablas

    public void limpiarTabla() {
        for (int i = 0; i < modeloTB.getRowCount(); i++) {
            modeloTB.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaEmpleado() {
        for (int i = 0; i < modelEmpleado.getRowCount(); i++) {
            modelEmpleado.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaProveedor() {
        for (int i = 0; i < modelProveedor.getRowCount(); i++) {
            modelProveedor.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaProducto() {
        for (int i = 0; i < modelProducto.getRowCount(); i++) {
            modelProducto.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaHistorialV() {
        for (int i = 0; i < modelHistorialVenta.getRowCount(); i++) {
            modelHistorialVenta.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaVentas() {
        for (int i = 0; i < modelVenta.getRowCount(); i++) {
            modelVenta.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaVentasFacturas() {
        for (int i = 0; i < modelHistorialVentaFactura.getRowCount(); i++) {
            modelHistorialVentaFactura.removeRow(i);
            i = i - 1;
        }
    }

    public void LimpiarTablaPrecioHistorico() {
        for (int i = 0; i < modelListaPrecio.getRowCount(); i++) {
            modelListaPrecio.removeRow(i);
            i = i - 1;
        }
    }

    public void LimpiarTablaPrecioHistoricoMov() {
        for (int i = 0; i < modelListaPrecioMovimiento.getRowCount(); i++) {
            modelListaPrecioMovimiento.removeRow(i);
            i = i - 1;
        }
    }

    public void LimpiarTablaProductoCustom() {
        for (int i = 0; i < modelPHproducto.getRowCount(); i++) {
            modelPHproducto.removeRow(i);
            i = i - 1;
        }
    }

    public void LimpiarTablaCompra() {
        for (int i = 0; i < modeloCompra.getRowCount(); i++) {
            modeloCompra.removeRow(i);
            i = i - 1;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        panelInfo = new javax.swing.JTabbedPane();
        tabClientes = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtIdClienteCLI = new javax.swing.JTextField();
        txtNombreClienteCLI = new javax.swing.JTextField();
        txtCorreoClienteCLI = new javax.swing.JTextField();
        txtDireccionClienteCLI = new javax.swing.JTextField();
        txtTelefonoClienteCLI = new javax.swing.JTextField();
        cbEstadoCLienteCLI = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbClientesCLI = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        btnNuevoClienteCLI = new javax.swing.JButton();
        btnModificarCLI = new javax.swing.JButton();
        btnGuardarCLI = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        txtsearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbTipoFiltroCliente = new javax.swing.JComboBox<>();
        ClienteBuscar = new javax.swing.JButton();
        ClienteBuscar1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        tabProveedores = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtIdProveedores = new javax.swing.JTextField();
        txtNombreProveedores = new javax.swing.JTextField();
        txtCorreoProveedores = new javax.swing.JTextField();
        txtTelefonoProveedores = new javax.swing.JTextField();
        txtubicacionProveedor = new javax.swing.JTextField();
        cbEstadoProveedores = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbProveedores = new javax.swing.JTable();
        btnNuevoProveedores = new javax.swing.JButton();
        btnModificarProveedores = new javax.swing.JButton();
        btnGuardarProveedores = new javax.swing.JButton();
        btncanelProveedor = new javax.swing.JButton();
        txtsearchproovedor = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        cmbproveedor = new javax.swing.JComboBox<>();
        refreshProveedores = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        tabEmpleados = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtIdEmpleados = new javax.swing.JTextField();
        txtNombreEmpleados = new javax.swing.JTextField();
        txtCorreoEmpleados = new javax.swing.JTextField();
        txtDireccionEmpleados = new javax.swing.JTextField();
        txtTelefonoEmpleados = new javax.swing.JTextField();
        cbOcupacionEmpleado = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbEmpleados = new javax.swing.JTable();
        btnNuevoEmpleado = new javax.swing.JButton();
        btnModificarEmpleado = new javax.swing.JButton();
        btnGuardarEmpleados = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        btnCancelEmp = new javax.swing.JButton();
        cbEstadoEmpleados1 = new javax.swing.JComboBox<>();
        btnAddUser = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txtsearchempleado = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        cmbempleado = new javax.swing.JComboBox<>();
        btnEliminarNV2 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        tabProductos = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtIdProductosProductos = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtNombreDelProducto = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable();
        btnAgregarProductos = new javax.swing.JButton();
        btnEliminarProductos = new javax.swing.JButton();
        txtprecioProd = new javax.swing.JTextField();
        txtStockProd = new javax.swing.JTextField();
        btnProdModificar = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnProdCancelar = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        tabHistorial = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtcodventa = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        btnsearchventa = new javax.swing.JButton();
        btnventarefresh = new javax.swing.JButton();
        cmbHventa = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        pnlCompras = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCidProducto = new javax.swing.JTextField();
        txtCdescrip = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtCcantidad = new javax.swing.JTextField();
        txtCprecio = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtCtotal = new javax.swing.JTextField();
        btnAgregarProductoCompra = new javax.swing.JButton();
        txtCeliminar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbnuevacompra = new javax.swing.JTable();
        jLabel46 = new javax.swing.JLabel();
        lblcompraTotal = new javax.swing.JLabel();
        txtCompraTipoPago = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        txtCompraNombreProveedor = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtidProveedorCompra = new javax.swing.JTextField();
        btnCfacturar = new javax.swing.JButton();
        btnCrefrescar = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtCMontoPagar = new javax.swing.JTextField();
        txtCCambio = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        tabNuevaVenta = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdProductoNV = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDescripcionProductoNV = new javax.swing.JTextField();
        txtCantidadNV = new javax.swing.JTextField();
        txtPrecioNV = new javax.swing.JTextField();
        txtTotalVN = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNuevaVenta = new javax.swing.JTable();
        btnVentaNV = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnAgregarNV = new javax.swing.JButton();
        btnEliminarNV = new javax.swing.JButton();
        txtIdClienteNV = new javax.swing.JTextField();
        txtarroba = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnRefrescar = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        cmbtipopago = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtCambio = new javax.swing.JTextField();
        txtMontoPagar = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbhistorialventaFactura = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jfecha2 = new com.toedter.calendar.JDateChooser();
        jfecha1 = new com.toedter.calendar.JDateChooser();
        btnsearfactura = new javax.swing.JButton();
        btnrefreshfactura = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbPHproducto = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbPHhistorial = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbPHhistorialprecio = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnsalirsistema = new javax.swing.JButton();
        btnEmpresa = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        panelInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadEmpleados(evt);
            }
        });

        tabClientes.setBackground(new java.awt.Color(0, 153, 153));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Id Cliente");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Nombre");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Correo");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Dirección");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Telefono");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Estado");

        txtIdClienteCLI.setText("Automatico");
        txtIdClienteCLI.setEnabled(false);
        txtIdClienteCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteCLIActionPerformed(evt);
            }
        });
        txtIdClienteCLI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdClienteCLIKeyReleased(evt);
            }
        });

        txtNombreClienteCLI.setEnabled(false);
        txtNombreClienteCLI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreClienteCLIKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteCLIKeyTyped(evt);
            }
        });

        txtCorreoClienteCLI.setEnabled(false);
        txtCorreoClienteCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoClienteCLIActionPerformed(evt);
            }
        });
        txtCorreoClienteCLI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoClienteCLIKeyReleased(evt);
            }
        });

        txtDireccionClienteCLI.setEnabled(false);
        txtDireccionClienteCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionClienteCLIActionPerformed(evt);
            }
        });
        txtDireccionClienteCLI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionClienteCLIKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteCLIKeyTyped(evt);
            }
        });

        txtTelefonoClienteCLI.setEnabled(false);
        txtTelefonoClienteCLI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteCLIKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteCLIKeyTyped(evt);
            }
        });

        cbEstadoCLienteCLI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Habilitado", "Deshabilitado"}));
        cbEstadoCLienteCLI.setEnabled(false);
        cbEstadoCLienteCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstadoCLienteCLIActionPerformed(evt);
            }
        });

        tbClientesCLI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Cliente", "Nombre", "Correo", "Direccion", "Telefono", "Estado"
            }
        ));
        tbClientesCLI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickTable(evt);
            }
        });
        jScrollPane2.setViewportView(tbClientesCLI);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Clientes");

        btnNuevoClienteCLI.setBackground(new java.awt.Color(0, 153, 153));
        btnNuevoClienteCLI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNuevoClienteCLI.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoClienteCLI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ingresar productos.png"))); // NOI18N
        btnNuevoClienteCLI.setText("Nuevo Cliente");
        btnNuevoClienteCLI.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoClienteCLI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoClienteCLIMouseClicked(evt);
            }
        });
        btnNuevoClienteCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteCLIActionPerformed(evt);
            }
        });

        btnModificarCLI.setBackground(new java.awt.Color(0, 153, 153));
        btnModificarCLI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnModificarCLI.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCLI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/historial ventas.png"))); // NOI18N
        btnModificarCLI.setText("Modificar Cliente");
        btnModificarCLI.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarCLI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarCLIMouseClicked(evt);
            }
        });
        btnModificarCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCLIActionPerformed(evt);
            }
        });

        btnGuardarCLI.setBackground(new java.awt.Color(0, 153, 153));
        btnGuardarCLI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGuardarCLI.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCLI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar productos.png"))); // NOI18N
        btnGuardarCLI.setText("Guardar");
        btnGuardarCLI.setEnabled(false);
        btnGuardarCLI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarCLIMouseClicked(evt);
            }
        });
        btnGuardarCLI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCLIActionPerformed(evt);
            }
        });

        btncancel.setBackground(new java.awt.Color(0, 153, 153));
        btncancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btncancel.setForeground(new java.awt.Color(204, 0, 0));
        btncancel.setText("Cancelar");
        btncancel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });

        txtsearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Filtrar por");

        cmbTipoFiltroCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "ID", "Telefono" }));
        cmbTipoFiltroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoFiltroClienteActionPerformed(evt);
            }
        });

        ClienteBuscar.setBackground(new java.awt.Color(0, 153, 153));
        ClienteBuscar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ClienteBuscar.setForeground(new java.awt.Color(255, 255, 255));
        ClienteBuscar.setText("Buscar");
        ClienteBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ClienteBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClienteBuscarActionPerformed(evt);
            }
        });

        ClienteBuscar1.setBackground(new java.awt.Color(0, 153, 153));
        ClienteBuscar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ClienteBuscar1.setForeground(new java.awt.Color(255, 255, 255));
        ClienteBuscar1.setText("Refrescar");
        ClienteBuscar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ClienteBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClienteBuscar1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("By: CiverlacTeam©");

        javax.swing.GroupLayout tabClientesLayout = new javax.swing.GroupLayout(tabClientes);
        tabClientes.setLayout(tabClientesLayout);
        tabClientesLayout.setHorizontalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel17)
                        .addGap(143, 143, 143)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbTipoFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(ClienteBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClienteBuscar1))
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(30, 30, 30)
                                .addComponent(txtNombreClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(36, 36, 36)
                                .addComponent(txtCorreoClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(22, 22, 22)
                                .addComponent(txtDireccionClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(24, 24, 24)
                                .addComponent(txtTelefonoClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(35, 35, 35)
                                .addComponent(cbEstadoCLienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(btnNuevoClienteCLI)
                                .addGap(10, 10, 10)
                                .addComponent(btnModificarCLI))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btnGuardarCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncancel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGap(937, 937, 937)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        tabClientesLayout.setVerticalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbTipoFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ClienteBuscar)
                                .addComponent(ClienteBuscar1)))))
                .addGap(17, 17, 17)
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel11))
                            .addComponent(txtIdClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel12))
                            .addComponent(txtNombreClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel13))
                            .addComponent(txtCorreoClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel14))
                            .addComponent(txtDireccionClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel15))
                            .addComponent(txtTelefonoClienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel16))
                            .addComponent(cbEstadoCLienteCLI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoClienteCLI)
                            .addComponent(btnModificarCLI))
                        .addGap(11, 11, 11)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarCLI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btncancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        panelInfo.addTab("Clientes", tabClientes);

        tabProveedores.setBackground(new java.awt.Color(0, 153, 153));
        tabProveedores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Nombre");
        tabProveedores.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Telefono");
        tabProveedores.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Correo");
        tabProveedores.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Direccion");
        tabProveedores.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Estado");
        tabProveedores.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        txtIdProveedores.setText("Automatico");
        txtIdProveedores.setEnabled(false);
        txtIdProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProveedoresActionPerformed(evt);
            }
        });
        tabProveedores.add(txtIdProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 201, -1));

        txtNombreProveedores.setEnabled(false);
        txtNombreProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProveedoresActionPerformed(evt);
            }
        });
        txtNombreProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreProveedoresKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedoresKeyTyped(evt);
            }
        });
        tabProveedores.add(txtNombreProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 201, -1));

        txtCorreoProveedores.setEnabled(false);
        txtCorreoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoProveedoresActionPerformed(evt);
            }
        });
        txtCorreoProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreoProveedoresKeyPressed(evt);
            }
        });
        tabProveedores.add(txtCorreoProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 201, -1));

        txtTelefonoProveedores.setEnabled(false);
        txtTelefonoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoProveedoresActionPerformed(evt);
            }
        });
        txtTelefonoProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedoresKeyReleased(evt);
            }
        });
        tabProveedores.add(txtTelefonoProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 201, -1));

        txtubicacionProveedor.setEnabled(false);
        txtubicacionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtubicacionProveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtubicacionProveedorKeyTyped(evt);
            }
        });
        tabProveedores.add(txtubicacionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 201, -1));

        cbEstadoProveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Habilitado", "Deshabilitado"}));
        cbEstadoProveedores.setEnabled(false);
        cbEstadoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstadoProveedoresActionPerformed(evt);
            }
        });
        tabProveedores.add(cbEstadoProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 201, -1));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        tbProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Proveedor", "Nombre", "Correo", "Telefono", "Direccion", "Estado"
            }
        ));
        tbProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClickedtbProveedor(evt);
            }
        });
        jScrollPane3.setViewportView(tbProveedores);

        tabProveedores.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 691, -1));

        btnNuevoProveedores.setBackground(new java.awt.Color(0, 153, 153));
        btnNuevoProveedores.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNuevoProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ingresar productos.png"))); // NOI18N
        btnNuevoProveedores.setText("Nuevo Proveedor");
        btnNuevoProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoProveedoresMouseClicked(evt);
            }
        });
        btnNuevoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedoresActionPerformed(evt);
            }
        });
        tabProveedores.add(btnNuevoProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, -1));

        btnModificarProveedores.setBackground(new java.awt.Color(0, 153, 153));
        btnModificarProveedores.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnModificarProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/historial ventas.png"))); // NOI18N
        btnModificarProveedores.setText("Modificar Proveedor");
        btnModificarProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProveedoresMouseClicked(evt);
            }
        });
        btnModificarProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProveedoresActionPerformed(evt);
            }
        });
        tabProveedores.add(btnModificarProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, -1, -1));

        btnGuardarProveedores.setBackground(new java.awt.Color(0, 153, 153));
        btnGuardarProveedores.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGuardarProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar productos.png"))); // NOI18N
        btnGuardarProveedores.setText("Guardar");
        btnGuardarProveedores.setEnabled(false);
        btnGuardarProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarProveedoresMouseClicked(evt);
            }
        });
        btnGuardarProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedoresActionPerformed(evt);
            }
        });
        tabProveedores.add(btnGuardarProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 135, -1));

        btncanelProveedor.setBackground(new java.awt.Color(0, 153, 153));
        btncanelProveedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btncanelProveedor.setForeground(new java.awt.Color(204, 0, 0));
        btncanelProveedor.setText("Cancelar");
        btncanelProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btncanelProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncanelProveedorActionPerformed(evt);
            }
        });
        tabProveedores.add(btncanelProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 400, 90, 40));

        txtsearchproovedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtsearchproovedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchproovedorKeyReleased(evt);
            }
        });
        tabProveedores.add(txtsearchproovedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 190, -1));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setText("Proveedores");
        tabProveedores.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 42, -1, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Id Cliente");
        tabProveedores.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 97, -1, -1));

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Buscar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        tabProveedores.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Filtrar por");
        tabProveedores.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        cmbproveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "ID", "Telefono" }));
        tabProveedores.add(cmbproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 110, 20));

        refreshProveedores.setBackground(new java.awt.Color(0, 153, 153));
        refreshProveedores.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        refreshProveedores.setForeground(new java.awt.Color(255, 255, 255));
        refreshProveedores.setText("Refrescar");
        refreshProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshProveedoresActionPerformed(evt);
            }
        });
        tabProveedores.add(refreshProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 90, -1));

        jLabel55.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("By: CiverlacTeam©");
        tabProveedores.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 610, 120, -1));

        panelInfo.addTab("Proveedores", tabProveedores);

        tabEmpleados.setBackground(new java.awt.Color(0, 153, 153));
        tabEmpleados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Nombre");
        tabEmpleados.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Direccion");
        tabEmpleados.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Correo");
        tabEmpleados.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Telefono");
        tabEmpleados.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Estado");
        tabEmpleados.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        txtIdEmpleados.setText("Automatico");
        txtIdEmpleados.setEnabled(false);
        txtIdEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdEmpleadosActionPerformed(evt);
            }
        });
        tabEmpleados.add(txtIdEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 199, -1));

        txtNombreEmpleados.setEnabled(false);
        txtNombreEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreEmpleadosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEmpleadosKeyTyped(evt);
            }
        });
        tabEmpleados.add(txtNombreEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 201, -1));

        txtCorreoEmpleados.setEnabled(false);
        txtCorreoEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoEmpleadosActionPerformed(evt);
            }
        });
        txtCorreoEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreoEmpleadosKeyPressed(evt);
            }
        });
        tabEmpleados.add(txtCorreoEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 201, -1));

        txtDireccionEmpleados.setEnabled(false);
        txtDireccionEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionEmpleadosActionPerformed(evt);
            }
        });
        txtDireccionEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionEmpleadosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionEmpleadosKeyTyped(evt);
            }
        });
        tabEmpleados.add(txtDireccionEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 201, -1));

        txtTelefonoEmpleados.setEnabled(false);
        txtTelefonoEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoEmpleadosKeyReleased(evt);
            }
        });
        tabEmpleados.add(txtTelefonoEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 201, -1));

        cbOcupacionEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gerente", "Vendedor"}));
        cbOcupacionEmpleado.setEnabled(false);
        cbOcupacionEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOcupacionEmpleadoActionPerformed(evt);
            }
        });
        tabEmpleados.add(cbOcupacionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 201, -1));

        tbEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Empleado", "Nombre", "Ocupacion ", "Correo", "Direccion", "Telefono", "Estado"
            }
        ));
        tbEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClickedTbEmpleado(evt);
            }
        });
        jScrollPane6.setViewportView(tbEmpleados);

        tabEmpleados.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 675, -1));

        btnNuevoEmpleado.setBackground(new java.awt.Color(0, 153, 153));
        btnNuevoEmpleado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNuevoEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ingresar productos.png"))); // NOI18N
        btnNuevoEmpleado.setText("Nuevo Empleado");
        btnNuevoEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoEmpleadoMouseClicked(evt);
            }
        });
        btnNuevoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEmpleadoActionPerformed(evt);
            }
        });
        tabEmpleados.add(btnNuevoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        btnModificarEmpleado.setBackground(new java.awt.Color(0, 153, 153));
        btnModificarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnModificarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/historial ventas.png"))); // NOI18N
        btnModificarEmpleado.setText("Modificar Empleado");
        btnModificarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarEmpleadoMouseClicked(evt);
            }
        });
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });
        tabEmpleados.add(btnModificarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, -1, -1));

        btnGuardarEmpleados.setBackground(new java.awt.Color(0, 153, 153));
        btnGuardarEmpleados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGuardarEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar productos.png"))); // NOI18N
        btnGuardarEmpleados.setText("Guardar");
        btnGuardarEmpleados.setEnabled(false);
        btnGuardarEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarEmpleadosMouseClicked(evt);
            }
        });
        btnGuardarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEmpleadosActionPerformed(evt);
            }
        });
        tabEmpleados.add(btnGuardarEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 135, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Ocupación ");
        tabEmpleados.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        btnCancelEmp.setBackground(new java.awt.Color(0, 153, 153));
        btnCancelEmp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCancelEmp.setForeground(new java.awt.Color(204, 0, 0));
        btnCancelEmp.setText("Cancelar");
        btnCancelEmp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancelEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEmpActionPerformed(evt);
            }
        });
        tabEmpleados.add(btnCancelEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 90, 40));

        cbEstadoEmpleados1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Habilitado", "Deshabilitado"}));
        cbEstadoEmpleados1.setEnabled(false);
        cbEstadoEmpleados1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstadoEmpleados1ActionPerformed(evt);
            }
        });
        tabEmpleados.add(cbEstadoEmpleados1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 201, -1));

        btnAddUser.setBackground(new java.awt.Color(0, 153, 153));
        btnAddUser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setText("Agregar Usuario");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });
        tabEmpleados.add(btnAddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 540, 150, 38));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setText("Empleados");
        tabEmpleados.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 42, -1, -1));

        txtsearchempleado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtsearchempleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchempleadoKeyReleased(evt);
            }
        });
        tabEmpleados.add(txtsearchempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 190, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Id Empleado");
        tabEmpleados.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 97, -1, -1));

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Buscar");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        tabEmpleados.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Filtrar por");
        tabEmpleados.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        cmbempleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "ID", "Telefono" }));
        tabEmpleados.add(cmbempleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 110, 20));

        btnEliminarNV2.setBackground(new java.awt.Color(0, 153, 153));
        btnEliminarNV2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEliminarNV2.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarNV2.setText("Refescar");
        btnEliminarNV2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarNV2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarNV2ActionPerformed(evt);
            }
        });
        tabEmpleados.add(btnEliminarNV2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 90, -1));

        jLabel56.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("By: CiverlacTeam©");
        tabEmpleados.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 610, 110, -1));

        panelInfo.addTab("Empleados", tabEmpleados);

        tabProductos.setBackground(new java.awt.Color(0, 153, 153));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Id Producto");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Precio");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Stock");

        txtIdProductosProductos.setEnabled(false);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Nombre del producto");

        txtNombreDelProducto.setEnabled(false);
        txtNombreDelProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreDelProductoActionPerformed(evt);
            }
        });
        txtNombreDelProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreDelProductoKeyReleased(evt);
            }
        });

        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id Producto", "Nombre del producto", "Precio", "Stock"
            }
        ));
        tbProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseProvedorClick(evt);
            }
        });
        jScrollPane4.setViewportView(tbProductos);
        if (tbProductos.getColumnModel().getColumnCount() > 0) {
            tbProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbProductos.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbProductos.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        btnAgregarProductos.setBackground(new java.awt.Color(0, 153, 153));
        btnAgregarProductos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProductos.setText("Guardar");
        btnAgregarProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarProductos.setEnabled(false);
        btnAgregarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductosActionPerformed(evt);
            }
        });

        btnEliminarProductos.setBackground(new java.awt.Color(0, 153, 153));
        btnEliminarProductos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEliminarProductos.setForeground(new java.awt.Color(204, 0, 0));
        btnEliminarProductos.setText("Eliminar");
        btnEliminarProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductosActionPerformed(evt);
            }
        });

        txtprecioProd.setEnabled(false);
        txtprecioProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprecioProdActionPerformed(evt);
            }
        });
        txtprecioProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecioProdKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecioProdKeyTyped(evt);
            }
        });

        txtStockProd.setEnabled(false);
        txtStockProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockProdActionPerformed(evt);
            }
        });
        txtStockProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockProdKeyReleased(evt);
            }
        });

        btnProdModificar.setBackground(new java.awt.Color(0, 153, 153));
        btnProdModificar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnProdModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnProdModificar.setText("Modificar");
        btnProdModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProdModificar.setEnabled(false);
        btnProdModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdModificarActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(0, 153, 153));
        btnInsert.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnInsert.setText("Ingresar");
        btnInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnProdCancelar.setBackground(new java.awt.Color(0, 153, 153));
        btnProdCancelar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnProdCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnProdCancelar.setText("Cancelar");
        btnProdCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProdCancelar.setEnabled(false);
        btnProdCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdCancelarActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("By: CiverlacTeam©");

        javax.swing.GroupLayout tabProductosLayout = new javax.swing.GroupLayout(tabProductos);
        tabProductos.setLayout(tabProductosLayout);
        tabProductosLayout.setHorizontalGroup(
            tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(tabProductosLayout.createSequentialGroup()
                        .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdProductosProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(txtNombreDelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(txtprecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(81, 81, 81)
                        .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabProductosLayout.createSequentialGroup()
                                .addComponent(btnAgregarProductos)
                                .addGap(18, 18, 18)
                                .addComponent(btnProdCancelar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminarProductos)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(tabProductosLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(btnInsert, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addGap(29, 29, 29)
                                .addComponent(btnProdModificar)
                                .addGap(218, 218, 218))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabProductosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        tabProductosLayout.setVerticalGroup(
            tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabProductosLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(btnProdModificar)
                    .addComponent(btnInsert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tabProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreDelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtprecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStockProd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregarProductos)
                        .addComponent(btnEliminarProductos)
                        .addComponent(btnProdCancelar))
                    .addComponent(txtIdProductosProductos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addContainerGap())
        );

        panelInfo.addTab("Productos", tabProductos);

        tabHistorial.setBackground(new java.awt.Color(0, 153, 153));

        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Vendedor", "Total"
            }
        ));
        tbVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbVentas);

        btnPdfVentas.setBackground(new java.awt.Color(0, 153, 153));
        btnPdfVentas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPdfVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnPdfVentas.setText("Boton generar pdf");
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel51.setText("Filtrar por");

        btnsearchventa.setBackground(new java.awt.Color(0, 153, 153));
        btnsearchventa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnsearchventa.setForeground(new java.awt.Color(255, 255, 255));
        btnsearchventa.setText("Buscar");
        btnsearchventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchventaActionPerformed(evt);
            }
        });

        btnventarefresh.setBackground(new java.awt.Color(0, 153, 153));
        btnventarefresh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnventarefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnventarefresh.setText("Refrescar");
        btnventarefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventarefreshActionPerformed(evt);
            }
        });

        cmbHventa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID Venta", "Cliente", "Vendedor" }));

        jLabel61.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("By: CiverlacTeam©");

        javax.swing.GroupLayout tabHistorialLayout = new javax.swing.GroupLayout(tabHistorial);
        tabHistorial.setLayout(tabHistorialLayout);
        tabHistorialLayout.setHorizontalGroup(
            tabHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHistorialLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(tabHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabHistorialLayout.createSequentialGroup()
                        .addComponent(btnPdfVentas)
                        .addGap(81, 81, 81)
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbHventa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcodventa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnsearchventa)
                        .addGap(18, 18, 18)
                        .addComponent(btnventarefresh)))
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabHistorialLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        tabHistorialLayout.setVerticalGroup(
            tabHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHistorialLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(tabHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPdfVentas)
                    .addComponent(txtcodventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(btnsearchventa)
                    .addComponent(btnventarefresh)
                    .addComponent(cmbHventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                .addComponent(jLabel61)
                .addContainerGap())
        );

        panelInfo.addTab("Historial De Ventas", tabHistorial);

        pnlCompras.setBackground(new java.awt.Color(0, 153, 153));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Id Producto");

        txtCidProducto.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCidProductoPropertyChange(evt);
            }
        });
        txtCidProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCidProductoVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCidProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCidProductoKeyTyped(evt);
            }
        });

        txtCdescrip.setEnabled(false);
        txtCdescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCdescripActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Descripcion");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Cantidad");

        txtCcantidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCcantidadMouseReleased(evt);
            }
        });
        txtCcantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCcantidadActionPerformed(evt);
            }
        });
        txtCcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCcantidadKeyReleased(evt);
            }
        });

        txtCprecio.setEnabled(false);
        txtCprecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCprecioActionPerformed(evt);
            }
        });
        txtCprecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCprecioKeyReleased(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Precio");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Total");

        txtCtotal.setEditable(false);

        btnAgregarProductoCompra.setBackground(new java.awt.Color(0, 153, 153));
        btnAgregarProductoCompra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProductoCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProductoCompra.setText("Agregar");
        btnAgregarProductoCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarProductoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoCompraActionPerformed(evt);
            }
        });

        txtCeliminar.setBackground(new java.awt.Color(0, 153, 153));
        txtCeliminar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCeliminar.setForeground(new java.awt.Color(204, 0, 0));
        txtCeliminar.setText("Eliminar");
        txtCeliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtCeliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCeliminarActionPerformed(evt);
            }
        });

        tbnuevacompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id Producto", "Descripcion", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane7.setViewportView(tbnuevacompra);
        if (tbnuevacompra.getColumnModel().getColumnCount() > 0) {
            tbnuevacompra.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbnuevacompra.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbnuevacompra.getColumnModel().getColumn(3).setPreferredWidth(100);
            tbnuevacompra.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        jLabel46.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel46.setText("Total a Pagar");

        lblcompraTotal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblcompraTotal.setText("(ingrese total)");

        txtCompraTipoPago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCompraTipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Mixto" }));
        txtCompraTipoPago.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtCompraTipoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtCompraTipoPagoItemStateChanged(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Medio de pago");

        txtCompraNombreProveedor.setEnabled(false);

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Nombre Proveedor");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setText("Id Proveedor");

        txtidProveedorCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidProveedorCompraActionPerformed(evt);
            }
        });
        txtidProveedorCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidProveedorCompraKeyReleased(evt);
            }
        });

        btnCfacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/venta.png"))); // NOI18N
        btnCfacturar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCfacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCfacturarActionPerformed(evt);
            }
        });

        btnCrefrescar.setBackground(new java.awt.Color(0, 153, 153));
        btnCrefrescar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCrefrescar.setForeground(new java.awt.Color(255, 255, 255));
        btnCrefrescar.setText("Refrescar");
        btnCrefrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCrefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrefrescarActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("By: CiverlacTeam©");

        jLabel63.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel63.setText("Monto a pagar");

        txtCMontoPagar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCMontoPagar.setText("0.00");
        txtCMontoPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCMontoPagarMouseClicked(evt);
            }
        });
        txtCMontoPagar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCMontoPagarKeyReleased(evt);
            }
        });

        txtCCambio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCCambio.setText("0.00");
        txtCCambio.setEnabled(false);

        jLabel64.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel64.setText("Cambio");

        javax.swing.GroupLayout pnlComprasLayout = new javax.swing.GroupLayout(pnlCompras);
        pnlCompras.setLayout(pnlComprasLayout);
        pnlComprasLayout.setHorizontalGroup(
            pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComprasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(txtidProveedorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addComponent(txtCompraNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCompraTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel64))
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addComponent(txtCMontoPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(txtCCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addComponent(jLabel46)
                .addGap(21, 21, 21)
                .addComponent(lblcompraTotal)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(pnlComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addContainerGap())
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCidProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCdescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addGap(18, 18, 18)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addGap(18, 18, 18)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(txtCtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarProductoCompra)
                        .addGap(18, 18, 18)
                        .addComponent(txtCeliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrefrescar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCfacturar)
                        .addGap(25, 25, 25))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComprasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlComprasLayout.setVerticalGroup(
            pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlComprasLayout.createSequentialGroup()
                            .addComponent(jLabel40)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlComprasLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel38)
                                .addComponent(jLabel39)
                                .addComponent(jLabel26))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCdescrip)
                                    .addComponent(txtCidProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCfacturar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCrefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCeliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarProductoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(15, 15, 15)
                        .addComponent(txtidProveedorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(15, 15, 15)
                        .addComponent(txtCompraNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlComprasLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(lblcompraTotal)))
                    .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlComprasLayout.createSequentialGroup()
                            .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel63)
                                .addComponent(jLabel64))
                            .addGap(13, 13, 13)
                            .addGroup(pnlComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCMontoPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlComprasLayout.createSequentialGroup()
                            .addComponent(jLabel47)
                            .addGap(15, 15, 15)
                            .addComponent(txtCompraTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(jLabel60)
                .addContainerGap())
        );

        panelInfo.addTab("Compras", pnlCompras);

        tabNuevaVenta.setBackground(new java.awt.Color(0, 153, 153));
        tabNuevaVenta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Id Producto");
        tabNuevaVenta.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 93, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Cantidad");
        tabNuevaVenta.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Precio");
        tabNuevaVenta.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Total");
        tabNuevaVenta.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, -1, -1));

        txtIdProductoNV.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtIdProductoNVPropertyChange(evt);
            }
        });
        txtIdProductoNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdProductoNVKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdProductoNVKeyTyped(evt);
            }
        });
        tabNuevaVenta.add(txtIdProductoNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 78, 28));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Descripcion");
        tabNuevaVenta.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        txtDescripcionProductoNV.setEditable(false);
        txtDescripcionProductoNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProductoNVActionPerformed(evt);
            }
        });
        tabNuevaVenta.add(txtDescripcionProductoNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 170, 28));

        txtCantidadNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCantidadNVMouseReleased(evt);
            }
        });
        txtCantidadNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadNVActionPerformed(evt);
            }
        });
        txtCantidadNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadNVKeyReleased(evt);
            }
        });
        tabNuevaVenta.add(txtCantidadNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 106, 28));

        txtPrecioNV.setEditable(false);
        txtPrecioNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioNVActionPerformed(evt);
            }
        });
        tabNuevaVenta.add(txtPrecioNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 121, 24));

        txtTotalVN.setEditable(false);
        tabNuevaVenta.add(txtTotalVN, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 105, 24));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tbNuevaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id Producto", "Descripcion", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane1.setViewportView(tbNuevaVenta);
        if (tbNuevaVenta.getColumnModel().getColumnCount() > 0) {
            tbNuevaVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbNuevaVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbNuevaVenta.getColumnModel().getColumn(3).setPreferredWidth(100);
            tbNuevaVenta.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        tabNuevaVenta.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 1025, 256));

        btnVentaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/venta.png"))); // NOI18N
        btnVentaNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaNVActionPerformed(evt);
            }
        });
        tabNuevaVenta.add(btnVentaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Cambio");
        tabNuevaVenta.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 390, -1, -1));

        lblTotal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblTotal.setText("(ingrese total)");
        tabNuevaVenta.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 430, -1, -1));

        btnAgregarNV.setBackground(new java.awt.Color(0, 153, 153));
        btnAgregarNV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarNV.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarNV.setText("Agregar");
        btnAgregarNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNVActionPerformed(evt);
            }
        });
        tabNuevaVenta.add(btnAgregarNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 90, 40));

        btnEliminarNV.setBackground(new java.awt.Color(0, 153, 153));
        btnEliminarNV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEliminarNV.setForeground(new java.awt.Color(204, 0, 0));
        btnEliminarNV.setText("Eliminar");
        btnEliminarNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarNVActionPerformed(evt);
            }
        });
        tabNuevaVenta.add(btnEliminarNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, -1, 40));

        txtIdClienteNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteNVActionPerformed(evt);
            }
        });
        txtIdClienteNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdClienteNVKeyReleased(evt);
            }
        });
        tabNuevaVenta.add(txtIdClienteNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 82, 41));

        txtarroba.setEnabled(false);
        tabNuevaVenta.add(txtarroba, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 201, 41));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Id Cliente");
        tabNuevaVenta.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, -1));

        btnRefrescar.setBackground(new java.awt.Color(0, 153, 153));
        btnRefrescar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRefrescar.setForeground(new java.awt.Color(255, 255, 255));
        btnRefrescar.setText("Refrescar");
        btnRefrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        tabNuevaVenta.add(btnRefrescar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, -1, 40));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Nombre Cliente");
        tabNuevaVenta.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 390, 93, -1));

        cmbtipopago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbtipopago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Mixto" }));
        cmbtipopago.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cmbtipopago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbtipopagoItemStateChanged(evt);
            }
        });
        tabNuevaVenta.add(cmbtipopago, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 420, 130, 40));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("Medio de pago");
        tabNuevaVenta.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, 93, -1));

        jLabel53.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel53.setText("Total a Pagar");
        tabNuevaVenta.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 390, -1, -1));

        jLabel54.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel54.setText("Monto a pagar");
        tabNuevaVenta.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 390, -1, -1));

        txtCambio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCambio.setText("0.00");
        txtCambio.setEnabled(false);
        tabNuevaVenta.add(txtCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 420, 100, 40));

        txtMontoPagar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMontoPagar.setText("0.00");
        txtMontoPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMontoPagarMouseClicked(evt);
            }
        });
        txtMontoPagar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMontoPagarKeyReleased(evt);
            }
        });
        tabNuevaVenta.add(txtMontoPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 420, 100, 40));

        jLabel59.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("By: CiverlacTeam©");
        tabNuevaVenta.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 610, 120, -1));

        panelInfo.addTab("Nueva venta", tabNuevaVenta);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));

        tbhistorialventaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ref", "Fecha emision", "Vendedor", "Cliente"
            }
        ));
        tbhistorialventaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbhistorialventaFacturaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbhistorialventaFactura);

        jPanel2.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 90, 871, 436));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText("Desde");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText("Hasta");
        jPanel2.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, -1, -1));

        jfecha2.setMaxSelectableDate(new java.util.Date());
        jPanel2.add(jfecha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 180, 30));

        jfecha1.setMaxSelectableDate(new java.util.Date());
        jPanel2.add(jfecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 180, 30));

        btnsearfactura.setBackground(new java.awt.Color(0, 153, 153));
        btnsearfactura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnsearfactura.setForeground(new java.awt.Color(255, 255, 255));
        btnsearfactura.setText("Buscar");
        btnsearfactura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnsearfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearfacturaActionPerformed(evt);
            }
        });
        jPanel2.add(btnsearfactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 33, 80, 40));

        btnrefreshfactura.setBackground(new java.awt.Color(0, 153, 153));
        btnrefreshfactura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnrefreshfactura.setForeground(new java.awt.Color(255, 255, 255));
        btnrefreshfactura.setText("Refrescar");
        btnrefreshfactura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnrefreshfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshfacturaActionPerformed(evt);
            }
        });
        jPanel2.add(btnrefreshfactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 33, -1, 40));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel50.setText("I: Hacer click sobre la factura que deseas imprimir.");
        jPanel2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, -1, -1));

        jLabel58.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("By: CiverlacTeam©");
        jPanel2.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(957, 610, 120, -1));

        panelInfo.addTab("Historial De Facturación", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbPHproducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Producto", "Nombre", "Precio Actual"
            }
        ));
        tbPHproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPHproductoMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbPHproducto);

        jPanel3.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 493, -1));

        tbPHhistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Periodo"
            }
        ));
        tbPHhistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPHhistorialMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tbPHhistorial);

        jPanel3.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 240, -1));

        tbPHhistorialprecio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Precio Movimiento"
            }
        ));
        jScrollPane11.setViewportView(tbPHhistorialprecio);

        jPanel3.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, 230, -1));

        jLabel57.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("By: CiverlacTeam©");
        jPanel3.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(957, 610, 120, -1));

        panelInfo.addTab("Precio Historico Productos", jPanel3);

        btnNuevaVenta.setBackground(new java.awt.Color(0, 153, 153));
        btnNuevaVenta.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNuevaVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/venta.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevaVentaMouseClicked(evt);
            }
        });
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(0, 153, 153));
        btnClientes.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ususario.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClientesMouseClicked(evt);
            }
        });
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedores.setBackground(new java.awt.Color(0, 153, 153));
        btnProveedores.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ususario.png"))); // NOI18N
        btnProveedores.setText("Proveedores");
        btnProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProveedoresMouseClicked(evt);
            }
        });
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(0, 153, 153));
        btnProductos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proructos.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(0, 153, 153));
        btnVentas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/historial ventas.png"))); // NOI18N
        btnVentas.setText("Historial de Ventas");
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVentasMouseClicked(evt);
            }
        });
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnEmpleados.setBackground(new java.awt.Color(0, 153, 153));
        btnEmpleados.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btnEmpleados.setText("Empleados");
        btnEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmpleadosMouseClicked(evt);
            }
        });
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 153, 153));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/venta.png"))); // NOI18N
        jButton5.setText("Compras");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 153, 153));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Precio Historico");
        jButton4.setMaximumSize(new java.awt.Dimension(113, 41));
        jButton4.setMinimumSize(new java.awt.Dimension(113, 41));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Historial de Facturacion");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnsalirsistema.setBackground(new java.awt.Color(0, 153, 153));
        btnsalirsistema.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsalirsistema.setForeground(new java.awt.Color(204, 0, 0));
        btnsalirsistema.setText("Salir");
        btnsalirsistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirsistemaActionPerformed(evt);
            }
        });

        btnEmpresa.setBackground(new java.awt.Color(0, 153, 153));
        btnEmpresa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        btnEmpresa.setText("Mi Empresa");
        btnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEmpleados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProductos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addGap(18, 18, 18)
                                .addComponent(btnsalirsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevaVenta)
                    .addComponent(btnClientes)
                    .addComponent(btnProveedores)
                    .addComponent(btnEmpleados)
                    .addComponent(btnProductos)
                    .addComponent(btnVentas)
                    .addComponent(jButton5)
                    .addComponent(btnsalirsistema))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -60, 1090, 830));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(1);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(4);
    }//GEN-LAST:event_btnVentasActionPerformed

    public void LimpiarTextfield() {
        txtTelefonoClienteCLI.setText(null);
        txtIdClienteCLI.setText("Automatico");
        txtNombreClienteCLI.setText(null);
        txtCorreoClienteCLI.setText(null);
        txtDireccionClienteCLI.setText(null);

    }
    private void btnClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseClicked

    }//GEN-LAST:event_btnClientesMouseClicked

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        panelInfo.setSelectedIndex(0);
        limpiarTabla();
        listaClientes();
    }//GEN-LAST:event_btnClientesActionPerformed

    public void ProvCancel() {
        txtIdProveedores.setEnabled(false);
        txtNombreProveedores.setEnabled(false);
        txtCorreoProveedores.setEnabled(false);
        txtTelefonoProveedores.setEnabled(false);
        txtubicacionProveedor.setEnabled(false);
        cbEstadoProveedores.setEnabled(false);
        btnModificarProveedores.setEnabled(true);
        btncanelProveedor.setVisible(false);
        btnGuardarProveedores.setEnabled(false);
        btnNuevoProveedores.setEnabled(true);
    }

    public void LimpiarProveedor() {

        txtIdProveedores.setText("Automatico");
        txtNombreProveedores.setText(null);
        txtCorreoProveedores.setText(null);
        txtTelefonoProveedores.setText(null);
        txtubicacionProveedor.setText(null);
        cbEstadoProveedores.setEnabled(false);
        btnModificarProveedores.setEnabled(true);
        btncanelProveedor.setVisible(false);
        btnGuardarProveedores.setEnabled(false);
        btnNuevoProveedores.setEnabled(true);

    }

    private void inActiveEmpleado() {
        txtIdEmpleados.setEnabled(false);
        txtNombreEmpleados.setEnabled(false);
        cbOcupacionEmpleado.setEnabled(false);
        txtCorreoEmpleados.setEnabled(false);
        txtDireccionEmpleados.setEnabled(false);
        txtTelefonoEmpleados.setEnabled(false);
        cbOcupacionEmpleado.setEnabled(false);
        cbEstadoEmpleados1.setEnabled(false);
    }

    private void ActiveEmpleado() {

        txtIdEmpleados.setEnabled(false);
        txtNombreEmpleados.setEnabled(true);
        cbOcupacionEmpleado.setEnabled(true);
        txtCorreoEmpleados.setEnabled(true);
        txtDireccionEmpleados.setEnabled(true);
        txtTelefonoEmpleados.setEnabled(true);
        cbOcupacionEmpleado.setEnabled(true);
        cbEstadoEmpleados1.setEnabled(true);
    }

    private void LimpiarEmpleado() {
        txtIdEmpleados.setText("Automatico");
        txtNombreEmpleados.setText(null);
        cbOcupacionEmpleado.setSelectedIndex(0);
        txtCorreoEmpleados.setText(null);
        txtDireccionEmpleados.setText(null);
        txtTelefonoEmpleados.setText(null);
        cbOcupacionEmpleado.setSelectedIndex(0);
    }
    double total = 0;
    double totalCompra = 0;

    public void limpiarVenta() {
        txtIdProductoNV.setText(null);
        txtDescripcionProductoNV.setText(null);
        txtCantidadNV.setText(null);
        txtPrecioNV.setText(null);
        txtTotalVN.setText(null);
    }

    public void limpiarCompra() {
        txtCidProducto.setText(null);
        txtCdescrip.setText(null);
        txtCcantidad.setText(null);
        txtCprecio.setText(null);
        txtCtotal.setText(null);
    }
    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void loadEmpleados(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadEmpleados
        // TODO add your handling code here:
        limpiarTablaVentasFacturas();
        listaHistorialVentaFacutra();
    }//GEN-LAST:event_loadEmpleados

    private void txtIdClienteNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdClienteNVKeyReleased
        // TODO add your handling code here:
        DatosCliente cliente = new DatosCliente();
        ConexionSQL cn = new ConexionSQL();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from `cliente` WHERE idCliente=?";

        double cantidad;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            if (txtIdClienteNV.getText() != "") {
                ps.setInt(1, Integer.parseInt(txtIdClienteNV.getText()));
                rs = ps.executeQuery();
                if (rs.next()) {
                    txtarroba.setText(rs.getString("Nombre"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setTelefono(rs.getString("idTelefonoCli"));

                } else {
                    txtarroba.setText(null);

                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }//GEN-LAST:event_txtIdClienteNVKeyReleased

    private void txtIdClienteNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteNVActionPerformed

    private void btnEliminarNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNVActionPerformed
        // TODO add your handling code here:
        VentasDB data = new VentasDB();
        if (tbNuevaVenta.getValueAt(tbNuevaVenta.getSelectedRow(), 4).toString() != "") {
            total -= Double.parseDouble(tbNuevaVenta.getValueAt(tbNuevaVenta.getSelectedRow(), 4).toString());
            data.plusStock(tbNuevaVenta.getValueAt(tbNuevaVenta.getSelectedRow(), 0).toString(),
                    Integer.parseInt(tbNuevaVenta.getValueAt(tbNuevaVenta.getSelectedRow(), 2).toString()));
            lblTotal.setText(total + "");

            if (cmbtipopago.getSelectedItem() != "Tarjeta") {
                txtCambio.setText((monto - total) + "");
            }
            DefaultTableModel dtm = (DefaultTableModel) tbNuevaVenta.getModel();
            dtm.removeRow(tbNuevaVenta.getSelectedRow());

            listaProd.remove(listaProd.size() - 1);

        }
    }//GEN-LAST:event_btnEliminarNVActionPerformed

    private void btnAgregarNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNVActionPerformed
        // TODO add your handling code here:
        VentasDB data = new VentasDB();
        datosProductoCompra dataProductos = new datosProductoCompra();
        int cantidad = Integer.parseInt(txtCantidadNV.getText());
        if (data.ValidarStock(txtIdProductoNV.getText()) < cantidad || Integer.parseInt(txtCantidadNV.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "Cantidad invalida\nLa existencia de este producto es: " + data.ValidarStock(txtIdProductoNV.getText()) + " unidades", "Error", JOptionPane.WARNING_MESSAGE);
            txtCantidadNV.setText(null);
            txtTotalVN.setText(null);
            txtCantidadNV.requestFocus();
        } else {

            if (txtIdProductoNV.getText().length() > 0 && txtCantidadNV.getText().length() > 0) {

                try {
                    int cant = Integer.parseInt(txtCantidadNV.getText());

                    modelVenta = (DefaultTableModel) tbNuevaVenta.getModel();
                    Object[] object = new Object[5];

                    //
//                    dataProductos.setIdprod(txtIdProductoNV.getText());
//                    dataProductos.setDesc(txtDescripcionProductoNV.getText());
//                    dataProductos.setCant(cant + "");;
//                    dataProductos.setPrecio(txtPrecioNV.getText());
//                    dataProductos.setTotal(txtTotalVN.getText());
//                    System.out.println(dataProductos.getDesc());
                    //
                    data.reducirStock(txtIdProductoNV.getText(), Integer.parseInt(txtCantidadNV.getText()));
                    //
                    boolean addProd = true;
                    if (tbNuevaVenta.getRowCount() > 0) {
                        for (int i = 0; i < tbNuevaVenta.getRowCount(); i++) {
                            if (tbNuevaVenta.getValueAt(i, 0).toString().equals(txtIdProductoNV.getText())) {
                                // Eliminar producto lista
                                listaProd.remove(i);
                                //
                                int cant1 = Integer.parseInt(tbNuevaVenta.getValueAt(i, 2).toString());
                                int cant2 = cant;
                                double total1 = Double.parseDouble(tbNuevaVenta.getValueAt(i, 4).toString()) + Double.parseDouble(txtTotalVN.getText());

                                tbNuevaVenta.setValueAt(cant + cant2, i, 2);
                                tbNuevaVenta.setValueAt(total1, i, 4);
                                dataProductos.setIdprod(tbNuevaVenta.getValueAt(i, 0).toString());
                                dataProductos.setDesc(tbNuevaVenta.getValueAt(i, 1).toString());
                                dataProductos.setCant(tbNuevaVenta.getValueAt(i, 2).toString());;
                                dataProductos.setPrecio(tbNuevaVenta.getValueAt(i, 3).toString());
                                dataProductos.setTotal(tbNuevaVenta.getValueAt(i, 4).toString());
                                System.out.println(tbNuevaVenta.getValueAt(i, 0).toString() + tbNuevaVenta.getValueAt(i, 1).toString() + tbNuevaVenta.getValueAt(i, 2).toString());
                                listaProd.add(dataProductos);
                                System.out.println("elproducto esta en el indice :" + i);
                                addProd = false;
                                break;
                            }
                        }
                        if (addProd) {
                            object[0] = txtIdProductoNV.getText();
                            object[1] = txtDescripcionProductoNV.getText();
                            object[2] = cant;
                            object[3] = txtPrecioNV.getText();
                            object[4] = txtTotalVN.getText();
                            //
                            dataProductos.setIdprod(txtIdProductoNV.getText());
                            dataProductos.setDesc(txtDescripcionProductoNV.getText());
                            dataProductos.setCant(cant + "");;
                            dataProductos.setPrecio(txtPrecioNV.getText());
                            dataProductos.setTotal(txtTotalVN.getText());
                            System.out.println(dataProductos.getDesc());
                            modelVenta.addRow(object);
                            tbNuevaVenta.setModel(modelVenta);
                            listaProd.add(dataProductos);
                        }

                    } else {
                        object[0] = txtIdProductoNV.getText();
                        object[1] = txtDescripcionProductoNV.getText();
                        object[2] = cant;
                        object[3] = txtPrecioNV.getText();
                        object[4] = txtTotalVN.getText();
                        //
                        dataProductos.setIdprod(txtIdProductoNV.getText());
                        dataProductos.setDesc(txtDescripcionProductoNV.getText());
                        dataProductos.setCant(cant + "");;
                        dataProductos.setPrecio(txtPrecioNV.getText());
                        dataProductos.setTotal(txtTotalVN.getText());
                        //System.out.println(dataProductos.getDesc());
                        //
                        modelVenta.addRow(object);
                        tbNuevaVenta.setModel(modelVenta);
                        listaProd.add(dataProductos);
                    }

                    //
                    //
                    total += Double.parseDouble(txtTotalVN.getText());
                    lblTotal.setText(total + "");
                    if (cmbtipopago.getSelectedItem() != "Tarjeta") {
                        txtCambio.setText((monto - total) + "");
                    }

                    limpiarVenta();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser un entero", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hay vampos vacios", "Error", JOptionPane.WARNING_MESSAGE);
            }

        }

    }//GEN-LAST:event_btnAgregarNVActionPerformed
    public void ClearVentaForm() {
        txtIdProductoNV.setText(null);
        txtDescripcionProductoNV.setText(null);
        txtCantidadNV.setText(null);
        txtPrecioNV.setText(null);
        txtTotalVN.setText(null);
        txtIdClienteNV.setText(null);
        txtarroba.setText(null);
        lblTotal.setText("0.00");
        limpiarTablaVentas();
    }
    private void btnVentaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaNVActionPerformed
        // rreccorrer Lista Productos
        Operacion = "venta";
        datosProductoCompra dataProductos = new datosProductoCompra();
//                for (int i = 0; i < tbNuevaVenta.getRowCount(); i++) {
//                    
//                        dataProductos.setIdprod(tbNuevaVenta.getValueAt(i, 0).toString());
//                        dataProductos.setDesc(tbNuevaVenta.getValueAt(i, 1).toString());
//                        dataProductos.setCant(tbNuevaVenta.getValueAt(i, 2).toString());;
//                        dataProductos.setPrecio(tbNuevaVenta.getValueAt(i, 3).toString());
//                        dataProductos.setTotal(tbNuevaVenta.getValueAt(i, 4).toString());
//                        System.out.println(tbNuevaVenta.getValueAt(i, 0).toString()+tbNuevaVenta.getValueAt(i, 1).toString()+tbNuevaVenta.getValueAt(i, 2).toString());
//                        
//                        
//                        listaProd.add(dataProductos);  
//                }    
        //

        if (txtIdClienteNV.getText().length() > 0 && txtarroba.getText().length() > 2) {

            try {
                valoresEstaticos vals = new valoresEstaticos();
                Ventas vnt = new Ventas();

                DatosCliente cliente = new DatosCliente();

                cliente.setCliente(txtarroba.getText());
                cliente.setIdCliente(Integer.parseInt(txtIdClienteNV.getText()));
                cliente.setEmpleado(vals.getIdUsuario() + "");
                String str = lblTotal.getText();
                str = str.substring(0, str.length() - 1);
                str = str.substring(0, str.length() - 1);
                cliente.setTotal(Double.parseDouble(str));

                vnt.setCliente(txtarroba.getText());
                vnt.setIdCliente(Integer.parseInt(txtIdClienteNV.getText()));
                vnt.setEmpleado(vals.getUsuario());
                vnt.setIdEmpleado(vals.getIdUsuario());
                vnt.setTotal(Double.parseDouble(lblTotal.getText()));
                vnt.setCambio(Double.parseDouble(txtCambio.getText()));

                VentasDB data = new VentasDB();
                if (cmbtipopago.getSelectedItem() == "Tarjeta") {
                    txtCambio.setText("0.00");
                    txtMontoPagar.setText("0.00");
                    data.RegistrarVenta(vnt, cmbtipopago.getSelectedIndex() + 1, "Tarje");
                    new DetalleFactura().setVisible(true);
                    ClearVentaForm();
                    listaProd = new ArrayList();
                    limpiarTablaProducto();
                    limpiarTablaHistorialV();
                    listaHistorialVenta();
                    total = 0;
                    txtMontoPagar.setText("0.00");
                    txtCambio.setText("0.00");
                } else if (cmbtipopago.getSelectedItem() == "Mixto") {
                    if (Double.parseDouble(txtMontoPagar.getText()) >= Double.parseDouble(lblTotal.getText())
                            || Double.parseDouble(txtMontoPagar.getText()) == 0) {
                        JOptionPane.showMessageDialog(null, "Para el pago mixto\nel monto debe ser mayor a 0 y menor que el total", "Error", JOptionPane.WARNING_MESSAGE);

                    } else {

                        double efectivo = Double.parseDouble(txtMontoPagar.getText());
                        double tarjeta = total - efectivo;
                        JOptionPane.showMessageDialog(null, "Registro de venta exitoso\nPago en efectivo: L" + efectivo + "\nSe debito de la tarjeta: L" + tarjeta);
                        data.RegistrarVenta(vnt, cmbtipopago.getSelectedIndex() + 1, "Mixto");
                        new DetalleFactura().setVisible(true);
                        ClearVentaForm();
                        listaProd = new ArrayList();
                        limpiarTablaProducto();
                        limpiarTablaHistorialV();
                        listaHistorialVenta();
                        total = 0;
                        txtMontoPagar.setText("0.00");
                        txtCambio.setText("0.00");

                    }
                } else if (cmbtipopago.getSelectedItem() == "Efectivo") {
                    if (Double.parseDouble(txtCambio.getText()) < 0) {
                        JOptionPane.showMessageDialog(null, "Monto a pagar invalido", "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        data.RegistrarVenta(vnt, cmbtipopago.getSelectedIndex() + 1, "efe");
                        new DetalleFactura().setVisible(true);
                        ClearVentaForm();
                        listaProd = new ArrayList();
                        limpiarTablaProducto();
                        limpiarTablaHistorialV();
                        listaHistorialVenta();
                        total = 0;
                        txtMontoPagar.setText("0.00");
                        txtCambio.setText("0.00");

                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "El campo cliente esta vacio", "Error", JOptionPane.WARNING_MESSAGE);

        }
        limpiarTablaProducto();
        listaProductos();


    }//GEN-LAST:event_btnVentaNVActionPerformed

    private void txtPrecioNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioNVActionPerformed

    private void txtCantidadNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadNVKeyReleased
        // TODO add your handling code here:

        if ("0".equals(txtCantidadNV.getText()) || txtCantidadNV.getText().startsWith("-")) {
            txtCantidadNV.setText(null);
        } else {

            double cant = Double.parseDouble(txtCantidadNV.getText());
            double precio = Double.parseDouble(txtPrecioNV.getText());
            double total = cant * precio;
            txtTotalVN.setText(total + "");

        }
    }//GEN-LAST:event_txtCantidadNVKeyReleased

    private void txtCantidadNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadNVActionPerformed

    private void txtCantidadNVMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCantidadNVMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCantidadNVMouseReleased

    private void txtDescripcionProductoNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProductoNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProductoNVActionPerformed

    private void txtIdProductoNVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdProductoNVKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtIdProductoNVKeyTyped

    private void txtIdProductoNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdProductoNVKeyReleased
        // TODO add your handling code here:
        ConexionSQL cn = new ConexionSQL();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from `producto` WHERE idProducto=?";

        double cantidad;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            if (txtIdProductoNV.getText() != "") {
                ps.setInt(1, Integer.parseInt(txtIdProductoNV.getText()));
                rs = ps.executeQuery();
                if (rs.next()) {
                    txtDescripcionProductoNV.setText(rs.getString("Nombre"));
                    txtPrecioNV.setText(rs.getString("precio"));
                } else {
                    txtDescripcionProductoNV.setText(null);
                    txtPrecioNV.setText(null);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }//GEN-LAST:event_txtIdProductoNVKeyReleased

    private void VentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VentaKeyPressed

    }//GEN-LAST:event_VentaKeyPressed

    private void txtIdProductoNVPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtIdProductoNVPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoNVPropertyChange

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        try {
            ConexionSQL con = new ConexionSQL();
            Connection conn = con.getConnection();

            JasperReport reporte = null;
            String path = "src\\Layouts\\VentasReport.jasper";

            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conn);

            JasperViewer view = new JasperViewer(jprint, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

        } catch (JRException ex) {

        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void tbVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbVentasMouseClicked

    private void btnProdCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdCancelarActionPerformed
        // TODO add your handling code here:
        inActivProducto();
        btnInsert.setEnabled(true);
        btnProdModificar.setEnabled(false);
        btnEliminarProductos.setEnabled(true);
        btnAgregarProductos.setEnabled(false);
        btnProdCancelar.setEnabled(false);
    }//GEN-LAST:event_btnProdCancelarActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        estado = "insert";
        LimpiarProducto();
        ActiveProducto();
        btnInsert.setEnabled(false);
        btnProdModificar.setEnabled(false);
        btnEliminarProductos.setEnabled(false);
        btnAgregarProductos.setEnabled(true);
        btnProdCancelar.setEnabled(true);
        txtIdProductosProductos.setText("#Auto");

    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnProdModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdModificarActionPerformed
        // TODO add your handling code here:
        estado = "modify";
        ActiveProducto();
        btnInsert.setEnabled(false);
        btnProdModificar.setEnabled(true);
        btnEliminarProductos.setEnabled(false);
        btnAgregarProductos.setEnabled(true);
        btnProdCancelar.setEnabled(true);
        txtIdProductosProductos.setEnabled(false);
    }//GEN-LAST:event_btnProdModificarActionPerformed

    private void txtStockProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockProdActionPerformed

    private void txtprecioProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprecioProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecioProdActionPerformed

    private void btnEliminarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductosActionPerformed
        // TODO add your handling code here:
        producto pro = new producto();
        pro.setId(Integer.parseInt(txtIdProductosProductos.getText()));
        ProductoDB prod = new ProductoDB();
        prod.DeleteProducto(pro);

        LimpiarProducto();
        limpiarTablaProducto();
        listaProductos();
        inActivProducto();
        UsuarioAcceso();

    }//GEN-LAST:event_btnEliminarProductosActionPerformed

    private void btnAgregarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductosActionPerformed
        // TODO add your handling code here:
        valoresEstaticos vals = new valoresEstaticos();
        if (txtIdProductosProductos.getText().length() > 0 && txtNombreDelProducto.getText().length() > 5
                && txtprecioProd.getText().length() > 0 && txtStockProd.getText().length() > 0) {

            double precio;
            int stock;
            try {

                precio = Double.parseDouble(txtprecioProd.getText());
                stock = Integer.parseInt(txtStockProd.getText());
                producto pro = new producto();

                pro.setNombre(txtNombreDelProducto.getText());
                pro.setPreciol(precio);
                pro.setStock(stock);
                ProductoDB prod = new ProductoDB();
                if (estado == "insert") {
                    pro.setId(Integer.parseInt("1"));
                    prod.registrarProducto(pro);
                    //
                    btnInsert.setEnabled(true);
                    btnEliminarProductos.setEnabled(true);
                    btnAgregarProductos.setEnabled(false);
                    btnProdCancelar.setEnabled(false);

                    limpiarTablaProducto();
                    listaProductos();
                    inActivProducto();
                    LimpiarProducto();
                    btnProdModificar.setEnabled(false);
                    LimpiarTablaPrecioHistorico();
                    LimpiarTablaProductoCustom();
                    listaProductoCustom();
                    UsuarioAcceso();
                    ////
                    java.util.Date fecha = new java.util.Date();
                    DateFormat f = new SimpleDateFormat("yyy-MM-dd");
                    String fechaActual = f.format(fecha);
                    prod.registrarPrecioHistorico(Integer.parseInt(txtIdProductosProductos.getText()), fechaActual, precio);
                } else if (estado == "modify") {

                    pro.setId(Integer.parseInt(txtIdProductosProductos.getText()));
                    prod.UpdateProducto(pro);
                    UsuarioAcceso();

                    //
                    java.util.Date fecha = new java.util.Date();
                    DateFormat f = new SimpleDateFormat("yyy-MM-dd");
                    String fechaActual = f.format(fecha);

                    if (prod.VerificarPrecioHistorico(fechaActual, pro.getId(), precio)) {
                        System.out.println("update preh");
//                        
                        prod.UpdatePrecioHistorico(fechaActual, pro.getId(), precio);
                        prod.registrarPrecioHistorico(pro.getId(), fechaActual, precio);
                    } else {
                        System.out.println("insert preh");
                        prod.registrarPrecioHistorico(pro.getId(), fechaActual, precio);
                    }

                } else {

                }
                btnInsert.setEnabled(true);
                btnEliminarProductos.setEnabled(true);
                btnAgregarProductos.setEnabled(false);
                btnProdCancelar.setEnabled(false);

                limpiarTablaProducto();
                listaProductos();
                inActivProducto();
                LimpiarProducto();
                btnProdModificar.setEnabled(false);
                LimpiarTablaPrecioHistorico();
                LimpiarTablaProductoCustom();
                listaProductoCustom();

            } catch (Exception ex) {

            }

        } else {
            JOptionPane.showMessageDialog(null, "Hay campos incorrectos", "Error", JOptionPane.WARNING_MESSAGE);
            txtNombreDelProducto.setText(null);

        }
    }//GEN-LAST:event_btnAgregarProductosActionPerformed

    private void MouseProvedorClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MouseProvedorClick
        // TODO add your handling code here:
        btnProdModificar.setEnabled(true);
        txtIdProductosProductos.setText(tbProductos.getValueAt(tbProductos.getSelectedRow(), 0).toString());
        txtNombreDelProducto.setText(tbProductos.getValueAt(tbProductos.getSelectedRow(), 1).toString());
        txtprecioProd.setText(tbProductos.getValueAt(tbProductos.getSelectedRow(), 2).toString());
        txtStockProd.setText(tbProductos.getValueAt(tbProductos.getSelectedRow(), 3).toString());
        UsuarioAcceso();

    }//GEN-LAST:event_MouseProvedorClick

    private void txtNombreDelProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreDelProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreDelProductoActionPerformed

    private void btnEliminarNV2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNV2ActionPerformed
        // TODO add your handling code here:
        limpiarTablaEmpleado();
        listaEmpleados();
    }//GEN-LAST:event_btnEliminarNV2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String TipoFiltro = cmbempleado.getSelectedItem().toString();
        System.out.println(TipoFiltro);
        limpiarTablaEmpleado();
        Empleado cl = new Empleado();
        List<Empleado> listaCl = null;
        String state = null;
        switch (TipoFiltro) {
            case "Nombre":

                listaCl = empleadodb.listarClientesBusqueda("Nombre", txtsearchempleado.getText());
                state = "true";
                break;

            case "ID":
                int id;
                try {

                    id = Integer.parseInt(txtsearchempleado.getText());
                    listaCl = empleadodb.listarClientesBusqueda("idEmpleado", Integer.toString(id));
                    state = "true";
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ID es un valor numerico", "Error", JOptionPane.WARNING_MESSAGE);
                    listaClientes();
                }

                break;

            case "Telefono":

                listaCl = empleadodb.listarClientesBusqueda("idTelefonoEmp", txtsearchempleado.getText());
                break;

        }
        if (state == "true") {
            modelEmpleado = (DefaultTableModel) tbEmpleados.getModel();
            Object[] obj = new Object[7];
            for (int i = 0; i < listaCl.size(); i++) {
                obj[0] = listaCl.get(i).getIdEmpleado();
                obj[1] = listaCl.get(i).getNombre();
                obj[2] = listaCl.get(i).getOcupacion();
                obj[3] = listaCl.get(i).getCorreo();
                obj[4] = listaCl.get(i).getDireccion();
                obj[5] = listaCl.get(i).getTelefono();
                obj[6] = listaCl.get(i).getEstado();

                modelEmpleado.addRow(obj);

            }
            tbEmpleados.setModel(modelEmpleado);

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtsearchempleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchempleadoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchempleadoKeyReleased

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        // TODO add your handling code here:
        new RegistroLayout().setVisible(true);
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void cbEstadoEmpleados1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstadoEmpleados1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstadoEmpleados1ActionPerformed

    private void btnCancelEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEmpActionPerformed
        // TODO add your handling code here:
        inActiveEmpleado();
        LimpiarEmpleado();
        tbEmpleados.setEnabled(true);
        btnNuevoEmpleado.setEnabled(true);
        btnModificarEmpleado.setEnabled(true);
        btnGuardarEmpleados.setEnabled(false);
        btnCancelEmp.setVisible(false);
    }//GEN-LAST:event_btnCancelEmpActionPerformed

    private void btnGuardarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEmpleadosActionPerformed

        String correo = txtCorreoEmpleados.getText();

        if (txtNombreEmpleados.getText().length() > 10) {
            if (txtDireccionEmpleados.getText().length() > 10) {

                if (correo.contains("@")) {

                    //evaluar correo
                    String correoFormat = txtCorreoEmpleados.getText();
                    String[] partes = correoFormat.split("@");
                    String cpart1 = partes[0];
                    String cpart2 = partes[1];

                    //convertir nombre
                    String correo1 = cpart1;
                    String correo2 = cpart2;
                    //
                    if (cpart1 == null || cpart1.equals("")) {
                        JOptionPane.showMessageDialog(null, "Formato de correo invalido", "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (correo.contains("gmail.com") || correo.contains("yahoo.com") || correo.contains("hotmail.com")
                            || correo.contains("gmail.es") || correo.contains("yahoo.es") || correo.contains("hotmail.es") || correo.contains("Ujcv.edu.hn")) {

                        String numero = txtTelefonoEmpleados.getText();

                        if (numero.length() > 8) {
                            JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 8 digito maximo", "Error", JOptionPane.WARNING_MESSAGE);

                        } else if (numero.length() < 8) {

                            JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 8 digitos minimo", "Error", JOptionPane.WARNING_MESSAGE);

                        } else {
                            if (numero.startsWith("2") || numero.startsWith("3") || numero.startsWith("7")
                                    || numero.startsWith("8") || numero.startsWith("9")) {

                                //evaluando nombre
                                String string = txtNombreEmpleados.getText();
                                String[] parts = string.split(" ");
                                String part1 = parts[0];
                                String part2 = parts[1];

                                //convertir nombre
                                String nombre1 = part1;
                                String nombre2 = part2;
                                //

                                String resultado1 = part1.toUpperCase().charAt(0) + part1.substring(1, part1.length()).toLowerCase();
                                String resultado2 = part2.toUpperCase().charAt(0) + part2.substring(1, part2.length()).toLowerCase();
                                //
                                String nombrex = resultado1 + " " + resultado2;
                                System.out.println("resultado : " + nombrex);

                                Empleado dart = new Empleado();

                                dart.setOcupacion(cbOcupacionEmpleado.getSelectedItem().toString());
                                dart.setNombre(nombrex);
                                dart.setCorreo(txtCorreoEmpleados.getText());
                                dart.setDireccion(txtDireccionEmpleados.getText());
                                dart.setTelefono(Integer.parseInt(txtTelefonoEmpleados.getText()));

                                if (cbEstadoEmpleados1.getSelectedIndex() == 0) {
                                    dart.setEstado(1);
                                } else {
                                    dart.setEstado(2);
                                }
                                EmpleadosDB ProvDB = new EmpleadosDB();
                                if (estado == "insert") {
                                    dart.setIdEmpleado(1);
                                    if (ProvDB.ValidarTelefonoEmpleado(txtTelefonoEmpleados.getText())) {
                                        JOptionPane.showMessageDialog(null, "El Telefono ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                        txtTelefonoEmpleados.setText(null);
                                    } else if (!ProvDB.registrarEmpleado(dart)) {
                                        JOptionPane.showMessageDialog(null, "El correo ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                        txtCorreoEmpleados.setText(null);
                                    } else {
                                        ClearFormEmpleado();
                                        UsuarioAcceso();
                                    }

                                } else if (estado == "update") {

                                    dart.setIdEmpleado(Integer.parseInt(txtIdEmpleados.getText()));
                                    ProvDB.UpdateEmpleado(dart);
                                    UsuarioAcceso();
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "El número de teléfono debe comenzar con 2, 3, 7, 8 ó 9");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Debe insertar una extensión de correo válida \n"
                                + "Ejemplo: civerlac@gmail.com",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Dirección de correo inválida", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Direccion invalida\nMinimo 10 caracteres", "Error", JOptionPane.WARNING_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Nombre invalido\nMinimo 10 caracteres", "Error", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_btnGuardarEmpleadosActionPerformed
    public void ClearFormEmpleado() {
        limpiarTablaEmpleado();
        listaEmpleados();
        LimpiarEmpleado();
        inActiveEmpleado();

        //
        tbEmpleados.setEnabled(true);
        btnNuevoEmpleado.setEnabled(true);
        btnModificarEmpleado.setEnabled(true);
        btnGuardarEmpleados.setEnabled(false);
        btnCancelEmp.setVisible(false);
    }
    private void btnGuardarEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarEmpleadosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarEmpleadosMouseClicked

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed
        // TODO add your handling code here:
        estado = "update";
        ActiveEmpleado();
        tbEmpleados.setEnabled(false);
        btnNuevoEmpleado.setEnabled(false);
        btnModificarEmpleado.setEnabled(false);
        btnGuardarEmpleados.setEnabled(true);
        btnCancelEmp.setVisible(true);
    }//GEN-LAST:event_btnModificarEmpleadoActionPerformed

    private void btnModificarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarEmpleadoMouseClicked

    private void btnNuevoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEmpleadoActionPerformed
        // TODO add your handling code here:
        estado = "insert";
        LimpiarEmpleado();
        ActiveEmpleado();

        tbEmpleados.setEnabled(false);
        btnNuevoEmpleado.setEnabled(false);
        btnModificarEmpleado.setEnabled(false);
        btnGuardarEmpleados.setEnabled(true);
        btnCancelEmp.setVisible(true);
    }//GEN-LAST:event_btnNuevoEmpleadoActionPerformed

    private void btnNuevoEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoEmpleadoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoEmpleadoMouseClicked

    private void MouseClickedTbEmpleado(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MouseClickedTbEmpleado
        // TODO add your handling code here:

        txtIdEmpleados.setText(tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 0).toString());
        txtNombreEmpleados.setText(tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 1).toString());
        String ocupacion = tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 2).toString();
        txtCorreoEmpleados.setText(tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 3).toString());
        txtDireccionEmpleados.setText(tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 4).toString());
        txtTelefonoEmpleados.setText(tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 5).toString());
        String state = tbEmpleados.getValueAt(tbEmpleados.getSelectedRow(), 6).toString();
        if (state == "1") {
            cbEstadoEmpleados1.setSelectedIndex(3);
        }

        if (ocupacion == "Vendedor") {
            cbEstadoEmpleados1.setSelectedIndex(2);
        }
    }//GEN-LAST:event_MouseClickedTbEmpleado

    private void cbOcupacionEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOcupacionEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbOcupacionEmpleadoActionPerformed

    private void txtDireccionEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionEmpleadosActionPerformed

    private void txtCorreoEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoEmpleadosActionPerformed

    private void txtIdEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdEmpleadosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        String TipoFiltro = cmbproveedor.getSelectedItem().toString();
        System.out.println(TipoFiltro);
        limpiarTabla();
        ClienteDB cl = new ClienteDB();
        List<Cliente> listaPV = null;
        String state = null;
        switch (TipoFiltro) {
            case "Nombre":

                listaPV = cliente.listarProveedorBusqueda("Nombre", txtsearchproovedor.getText());
                state = "true";
                break;

            case "ID":
                int id;
                try {

                    id = Integer.parseInt(txtsearchproovedor.getText());
                    listaPV = cliente.listarProveedorBusqueda("idProveedor", Integer.toString(id));
                    state = "true";
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ID es un valor numerico", "Error", JOptionPane.WARNING_MESSAGE);
                    listaClientes();
                }

                break;

            case "Telefono":

                listaPV = cliente.listarProveedorBusqueda("TelefonoPro", txtsearchproovedor.getText());
                state = "true";
                break;

        }
        if (state == "true") {
            limpiarTablaProveedor();
            modelProveedor = (DefaultTableModel) tbProveedores.getModel();
            Object[] obj = new Object[6];
            for (int i = 0; i < listaPV.size(); i++) {
                obj[0] = listaPV.get(i).getIdCliente();
                obj[1] = listaPV.get(i).getNombre();
                obj[2] = listaPV.get(i).getCorreo();
                obj[3] = listaPV.get(i).getDireccion();
                obj[4] = listaPV.get(i).getIdTelefonoCli();
                obj[5] = listaPV.get(i).getEstado();

                modelProveedor.addRow(obj);

            }
            tbProveedores.setModel(modelProveedor);

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtsearchproovedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchproovedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchproovedorKeyReleased

    private void btncanelProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncanelProveedorActionPerformed
        // TODO add your handling code here:
        ProvCancel();
        LimpiarProveedor();
    }//GEN-LAST:event_btncanelProveedorActionPerformed

    private void btnGuardarProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedoresActionPerformed
        String correo = txtCorreoProveedores.getText();

        if (txtNombreProveedores.getText().length() > 10) {
            if (txtubicacionProveedor.getText().length() > 10) {

                if (correo.contains("@")) {

                    //evaluar correo
                    String correoFormat = txtCorreoProveedores.getText();
                    String[] partes = correoFormat.split("@");
                    String cpart1 = partes[0];
                    String cpart2 = partes[1];

                    //convertir nombre
                    String correo1 = cpart1;
                    String correo2 = cpart2;
                    //
                    if (cpart1 == null || cpart1.equals("")) {
                        JOptionPane.showMessageDialog(null, "Formato de correo invalido", "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (correo.contains("gmail.com") || correo.contains("yahoo.com") || correo.contains("hotmail.com") || correo.contains("ujcv.edu.hn")
                            || correo.contains("gmail.es") || correo.contains("yahoo.es") || correo.contains("hotmail.es")) {
                        try {

                            String numero = txtTelefonoProveedores.getText();
                            if (numero.length() > 8) {
                                JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 8 digito maximo", "Error", JOptionPane.WARNING_MESSAGE);

                            } else if (numero.length() < 8) {

                                JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 8 digitos minimo", "Error", JOptionPane.WARNING_MESSAGE);

                            } else {
                                if (numero.startsWith("2") || numero.startsWith("3") || numero.startsWith("7")
                                        || numero.startsWith("8") || numero.startsWith("9")) {

                                    String string = txtNombreProveedores.getText();
                                    String[] parts = string.split(" ");
                                    String part1 = parts[0];
                                    String part2 = parts[1];

                                    //convertir nombre
                                    String nombre1 = part1;
                                    String nombre2 = part2;
                                    //

                                    String resultado1 = part1.toUpperCase().charAt(0) + part1.substring(1, part1.length()).toLowerCase();
                                    String resultado2 = part2.toUpperCase().charAt(0) + part2.substring(1, part2.length()).toLowerCase();
                                    //
                                    String nombrex = resultado1 + " " + resultado2;
                                    System.out.println("resultado : " + nombrex);
                                    //

                                    Cliente dart = new Cliente();

                                    dart.setNombre(nombrex);
                                    dart.setCorreo(txtCorreoProveedores.getText());
                                    dart.setDireccion(txtubicacionProveedor.getText());
                                    dart.setIdTelefonoCli(Integer.parseInt(txtTelefonoProveedores.getText()));

                                    if (cbEstadoProveedores.getSelectedIndex() == 0) {
                                        dart.setEstado(1);
                                    } else {
                                        dart.setEstado(2);
                                    }
                                    ProveedorDB ProvDB = new ProveedorDB();
                                    if (estado == "insert") {
                                        dart.setIdCliente(1);

                                        if (ProvDB.ValidarTelefonoProovedor(txtTelefonoProveedores.getText())) {
                                            JOptionPane.showMessageDialog(null, "El Telefono ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                            txtTelefonoProveedores.setText(null);

                                        } else if (ProvDB.registrarProveedor(dart)) {
                                            JOptionPane.showMessageDialog(null, "El correo ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                            txtCorreoProveedores.setText(null);
                                        } else {
                                            ClearFormProveedor();
                                            UsuarioAcceso();
                                        }

                                    } else if (estado == "update") {
                                        dart.setIdCliente(Integer.parseInt(txtIdProveedores.getText()));

                                        if (ProvDB.ValidarUpdateTelefonoProveedor(txtTelefonoProveedores.getText(), dart.getIdCliente()) != 0) {
                                            JOptionPane.showMessageDialog(null, "El Telefono ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                            txtTelefonoProveedores.setText(null);
                                        } else if (!ProvDB.UpdateProveedor(dart)) {
                                            JOptionPane.showMessageDialog(null, "El correo ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                            txtCorreoProveedores.setText(null);
                                        } else {
                                            ClearFormProveedor();
                                            UsuarioAcceso();
                                        }

                                    }

                                } else {
                                    JOptionPane.showMessageDialog(null, "El número de teléfono debe comenzar con 2, 3, 7, 8 ó 9", "Error", JOptionPane.WARNING_MESSAGE);
                                }
                            }

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(null, "Caracteres invalidos" + ex, "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Debe insertar una extensión de correo válida \n"
                                + "Ejemplo: civerlac@gmail.com",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Dirección de correo inválida", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Direccion invalida\nMinimo 10 caracteres", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nombre invalido\nMinimo 10 caracteres", "Error", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_btnGuardarProveedoresActionPerformed
    public void ClearFormProveedor() {
        ProveedorDB ProvDB = new ProveedorDB();
        ProvDB.listarClientes();
        limpiarTablaProveedor();
        listaProveedores();
        tbProveedores.setEnabled(true);
        LimpiarProveedor();
        ProvCancel();
        LimpiarProveedor();
    }
    private void btnGuardarProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProveedoresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarProveedoresMouseClicked

    private void btnModificarProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProveedoresActionPerformed
        // TODO add your handling code here:
        estado = "update";
        txtIdProveedores.setEnabled(false);
        txtNombreProveedores.setEnabled(true);
        txtCorreoProveedores.setEnabled(true);
        txtTelefonoProveedores.setEnabled(true);
        txtubicacionProveedor.setEnabled(true);
        cbEstadoProveedores.setEnabled(true);
        btnModificarProveedores.setEnabled(false);
        btncanelProveedor.setVisible(true);
        btnGuardarProveedores.setEnabled(true);
        btnNuevoProveedores.setEnabled(false);
    }//GEN-LAST:event_btnModificarProveedoresActionPerformed

    private void btnModificarProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedoresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarProveedoresMouseClicked

    private void btnNuevoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedoresActionPerformed

        LimpiarProveedor();
        estado = "insert";
        txtIdProveedores.setEnabled(false);
        txtIdProveedores.setText("Automatico");
        txtNombreProveedores.setEnabled(true);
        txtCorreoProveedores.setEnabled(true);
        txtTelefonoProveedores.setEnabled(true);
        txtubicacionProveedor.setEnabled(true);
        cbEstadoProveedores.setEnabled(true);
        btnModificarProveedores.setEnabled(false);
        btncanelProveedor.setVisible(true);
        btnGuardarProveedores.setEnabled(true);
        btnNuevoProveedores.setEnabled(false);
    }//GEN-LAST:event_btnNuevoProveedoresActionPerformed

    private void btnNuevoProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoProveedoresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoProveedoresMouseClicked

    private void MouseClickedtbProveedor(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MouseClickedtbProveedor
        // TODO add your handling code here:
        txtIdProveedores.setText(tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 0).toString());
        txtNombreProveedores.setText(tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 1).toString());
        txtCorreoProveedores.setText(tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 2).toString());
        txtTelefonoProveedores.setText(tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 3).toString());
        txtubicacionProveedor.setText(tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 4).toString());
        String state = tbProveedores.getValueAt(tbProveedores.getSelectedRow(), 5).toString();
        if (state == "1") {
            cbEstadoProveedores.setSelectedIndex(3);
        }
        UsuarioAcceso();
    }//GEN-LAST:event_MouseClickedtbProveedor

    private void cbEstadoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstadoProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstadoProveedoresActionPerformed

    private void txtTelefonoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProveedoresActionPerformed

    private void txtCorreoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoProveedoresActionPerformed

    private void txtIdProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProveedoresActionPerformed

    private void ClienteBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClienteBuscar1ActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
        listaClientes();
        txtsearch.setText(null);
    }//GEN-LAST:event_ClienteBuscar1ActionPerformed

    private void ClienteBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClienteBuscarActionPerformed
        // TODO add your handling code here:
        String TipoFiltro = cmbTipoFiltroCliente.getSelectedItem().toString();
        System.out.println(TipoFiltro);
        limpiarTabla();
        ClienteDB cl = new ClienteDB();
        List<Cliente> listaCl = null;
        String state = null;
        switch (TipoFiltro) {
            case "Nombre":

                listaCl = cliente.listarClientesBusqueda("Nombre", txtsearch.getText());
                state = "true";
                break;

            case "ID":
                int id;
                try {

                    id = Integer.parseInt(txtsearch.getText());
                    listaCl = cliente.listarClientesBusqueda("idCliente", Integer.toString(id));
                    state = "true";
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ID es un valor numerico", "Error", JOptionPane.WARNING_MESSAGE);
                    listaClientes();
                }

                break;

            case "Telefono":

                listaCl = cliente.listarClientesBusqueda("idTelefonoCli", txtsearch.getText());
                state = "true";
                break;

        }
        if (state == "true") {
            modeloTB = (DefaultTableModel) tbClientesCLI.getModel();
            Object[] obj = new Object[6];
            for (int i = 0; i < listaCl.size(); i++) {
                obj[0] = listaCl.get(i).getIdCliente();
                obj[1] = listaCl.get(i).getNombre();
                obj[2] = listaCl.get(i).getCorreo();
                obj[3] = listaCl.get(i).getDireccion();
                obj[4] = listaCl.get(i).getIdTelefonoCli();
                obj[5] = listaCl.get(i).getEstado();

                modeloTB.addRow(obj);

            }
            tbClientesCLI.setModel(modeloTB);

        }

    }//GEN-LAST:event_ClienteBuscarActionPerformed

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtsearchKeyReleased

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        // TODO add your handling code here:
        txtIdClienteCLI.setText("Automatico");
        CancelProcess();
    }//GEN-LAST:event_btncancelActionPerformed

    private void btnGuardarCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCLIActionPerformed

//         if (!"".equals(txtIdClienteCLI.getText()) && !"".equals(txtNombreClienteCLI.getText()) && !"".equals(txtCorreoClienteCLI.getText()) && !"".equals(txtDireccionClienteCLI.getText()) && !"".equals(txtTelefonoClienteCLI.getText()) && !"".equals(cbEstadoCLienteCLI.getSelectedItem())) {
//
//            cl.setIdCliente(Integer.parseInt(txtIdClienteCLI.getText()));
//            cl.setNombre(txtNombreClienteCLI.getText());
//            cl.setCorreo(txtCorreoClienteCLI.getText());
//            cl.setDireccion(txtDireccionClienteCLI.getText());
//            cl.setIdTelefonoCli(Integer.parseInt(txtTelefonoClienteCLI.getText()));
//            // cl.setEstado((String) cbEstadoCLienteCLI.getSelectedItem());
//            cliente.registrarClientes(cl);
//            JOptionPane.showMessageDialog(null, "Cliente Registrado con exito");
//
//            btnGuardarCLI.setEnabled(false);
//            btnNuevoClienteCLI.setEnabled(true);
//            btnModificarCLI.setEnabled(true);
//            txtCorreoClienteCLI.setEnabled(false);
//            txtDireccionClienteCLI.setEnabled(false);
//            txtTelefonoClienteCLI.setEnabled(false);
//            cbEstadoCLienteCLI.setEnabled(false);
//        } else {
//            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
//        }
//
//        limpiarTabla();
//        listaClientes();
        String correo = txtCorreoClienteCLI.getText();

        if (txtNombreClienteCLI.getText().length() > 10) {

            if (txtDireccionClienteCLI.getText().length() > 10) {

                if (correo.contains("@")) {

                    //evaluar correo
                    String correoFormat = txtCorreoClienteCLI.getText();
                    String[] partes = correoFormat.split("@");
                    String cpart1 = partes[0];
                    String cpart2 = partes[1];

                    //convertir nombre
                    String correo1 = cpart1;
                    String correo2 = cpart2;
                    //
                    if (cpart1 == null || cpart1.equals("")) {
                        JOptionPane.showMessageDialog(null, "Formato de correo invalido", "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (correo.contains("gmail.com") || correo.contains("yahoo.com") || correo.contains("hotmail.com")
                            || correo.contains("gmail.es") || correo.contains("yahoo.es") || correo.contains("hotmail.es") || correo.contains("ujcv.edu.hn")) {
                        if (txtDireccionClienteCLI.getText().length() > 8) {
                            try {
                                String numero = txtTelefonoClienteCLI.getText();

                                if (numero.length() > 8) {
                                    JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 8 digito maximo", "Error", JOptionPane.WARNING_MESSAGE);

                                } else if (numero.length() < 8) {

                                    JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 8 digitos minimo", "Error", JOptionPane.WARNING_MESSAGE);

                                } else {
                                    if (numero.startsWith("2") || numero.startsWith("3") || numero.startsWith("7")
                                            || numero.startsWith("8") || numero.startsWith("9")) {

                                        //
                                        // convertir nombres a formato correcto
                                        String string = txtNombreClienteCLI.getText();
                                        String[] parts = string.split(" ");
                                        String part1 = parts[0];
                                        String part2 = parts[1];

                                        //convertir nombre
                                        String nombre1 = part1;
                                        String nombre2 = part2;
                                        //

                                        String resultado1 = part1.toUpperCase().charAt(0) + part1.substring(1, part1.length()).toLowerCase();
                                        String resultado2 = part2.toUpperCase().charAt(0) + part2.substring(1, part2.length()).toLowerCase();
                                        //
                                        String nombrex = resultado1 + " " + resultado2;
                                        System.out.println("resultado : " + nombrex);
                                        //

                                        Cliente dart = new Cliente();
                                        //System.out.println(txtNombreClienteCLI.getText());
                                        dart.setNombre(nombrex);
                                        dart.setCorreo(txtCorreoClienteCLI.getText());
                                        dart.setDireccion(txtDireccionClienteCLI.getText());
                                        dart.setIdTelefonoCli(Integer.parseInt(txtTelefonoClienteCLI.getText()));

                                        if (cbEstadoCLienteCLI.getSelectedIndex() == 0) {
                                            dart.setEstado(1);
                                        } else {
                                            dart.setEstado(2);
                                        }
                                        ClienteDB dbClient = new ClienteDB();
                                        if (estado == "insert") {
                                            dart.setIdCliente(Integer.parseInt("1"));
                                            if (dbClient.ValidarTelefonoCliente(txtTelefonoClienteCLI.getText())) {
                                                JOptionPane.showMessageDialog(null, "El Telefono ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                                txtTelefonoClienteCLI.setText(null);
                                            } else if (!dbClient.registrarClientes(dart)) {
                                                JOptionPane.showMessageDialog(null, "El correo electronico ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                                txtCorreoClienteCLI.setText(null);
                                            } else {
                                                ClearClienteForm();
                                                UsuarioAcceso();
                                            }

                                        } else if (estado == "update") {
                                            dart.setIdCliente(Integer.parseInt(txtIdClienteCLI.getText()));

                                            if (dbClient.ValidarUpdateTelefonoCliente(txtTelefonoClienteCLI.getText(), dart.getIdCliente()) != 0) {
                                                JOptionPane.showMessageDialog(null, "El Telefono ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                                txtTelefonoClienteCLI.setText(null);
                                            } else if (!dbClient.UpdateClientes(dart)) {
                                                JOptionPane.showMessageDialog(null, "El correo electronico ya esta registrado", "Error", JOptionPane.WARNING_MESSAGE);
                                                txtCorreoClienteCLI.setText(null);
                                            } else {
                                                ClearClienteForm();
                                                UsuarioAcceso();
                                            }
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, "El número de teléfono debe comenzar con 2, 3, 7, 8 ó 9", "Error", JOptionPane.WARNING_MESSAGE);
                                    }
                                }

                            } catch (Exception ex) {

                                JOptionPane.showMessageDialog(null, "Caracteres invalidos", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Direccion invalida", "Error", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Debe insertar una extensión de correo válida \n"
                                + "Ejemplo: civerlac@gmail.com",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Dirección de correo inválida", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else {

                JOptionPane.showMessageDialog(null, "Direccion invalida\nMinimo 10 caracteres", "Error", JOptionPane.WARNING_MESSAGE);

            }

        } else {
            JOptionPane.showMessageDialog(null, "Nombre invalido\nMinimo 10 caracteres\nRecuerda ingresar nombre y apellido", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarCLIActionPerformed
    public void ClearClienteForm() {
        ClienteDB dbClient = new ClienteDB();
        tbClientesCLI.setEnabled(true);
        dbClient.listarClientes();
        LimpiarTextfield();
        limpiarTabla();
        listaClientes();
        btnGuardarCLI.setEnabled(false);
        btnNuevoClienteCLI.setEnabled(true);
        btnModificarCLI.setEnabled(true);

        txtIdClienteCLI.setEnabled(false);
        txtNombreClienteCLI.setEnabled(false);
        txtCorreoClienteCLI.setEnabled(false);
        txtCorreoClienteCLI.setEnabled(false);
        txtDireccionClienteCLI.setEnabled(false);
        txtTelefonoClienteCLI.setEnabled(false);
        cbEstadoCLienteCLI.setEnabled(false);
        btncancel.setVisible(false);
    }
    private void btnGuardarCLIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCLIMouseClicked

    }//GEN-LAST:event_btnGuardarCLIMouseClicked

    private void btnModificarCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCLIActionPerformed
        // TODO add your handling code here:
        btnGuardarCLI.setEnabled(true);
        btnNuevoClienteCLI.setEnabled(false);
        btnModificarCLI.setEnabled(false);
        txtCorreoClienteCLI.setEnabled(true);
        txtDireccionClienteCLI.setEnabled(true);
        txtTelefonoClienteCLI.setEnabled(true);
        cbEstadoCLienteCLI.setEnabled(true);
        txtNombreClienteCLI.setEnabled(true);
//
        estado = "update";
        btncancel.setVisible(true);
        UsuarioAcceso();

    }//GEN-LAST:event_btnModificarCLIActionPerformed

    private void btnModificarCLIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarCLIMouseClicked

    }//GEN-LAST:event_btnModificarCLIMouseClicked

    private void btnNuevoClienteCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteCLIActionPerformed

        btnGuardarCLI.setEnabled(true);
        btnNuevoClienteCLI.setEnabled(false);
        btnModificarCLI.setEnabled(false);

        txtIdClienteCLI.setText("Automatico");
        txtNombreClienteCLI.setText("");
        txtCorreoClienteCLI.setText("");
        txtCorreoClienteCLI.setEnabled(true);

        txtDireccionClienteCLI.setText("");
        txtDireccionClienteCLI.setEnabled(true);

        txtTelefonoClienteCLI.setText("");
        txtTelefonoClienteCLI.setEnabled(true);

        cbEstadoCLienteCLI.setSelectedItem("");
        cbEstadoCLienteCLI.setEnabled(true);
//
        tbClientesCLI.setEnabled(false);
        txtNombreClienteCLI.setEnabled(true);
        txtIdClienteCLI.setText("Automatico");
        estado = "insert";
        btncancel.setVisible(true);
        UsuarioAcceso();
    }//GEN-LAST:event_btnNuevoClienteCLIActionPerformed

    private void btnNuevoClienteCLIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoClienteCLIMouseClicked

    }//GEN-LAST:event_btnNuevoClienteCLIMouseClicked

    private void mouseClickTable(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClickTable
        // TODO add your handling code here:
        txtIdClienteCLI.setText(tbClientesCLI.getValueAt(tbClientesCLI.getSelectedRow(), 0).toString());
        txtNombreClienteCLI.setText(tbClientesCLI.getValueAt(tbClientesCLI.getSelectedRow(), 1).toString());
        txtCorreoClienteCLI.setText(tbClientesCLI.getValueAt(tbClientesCLI.getSelectedRow(), 2).toString());
        txtDireccionClienteCLI.setText(tbClientesCLI.getValueAt(tbClientesCLI.getSelectedRow(), 3).toString());
        txtTelefonoClienteCLI.setText(tbClientesCLI.getValueAt(tbClientesCLI.getSelectedRow(), 4).toString());
        String state = tbClientesCLI.getValueAt(tbClientesCLI.getSelectedRow(), 5).toString();
        if (state == "1") {
            cbEstadoCLienteCLI.setSelectedIndex(3);
        }
        UsuarioAcceso();
    }//GEN-LAST:event_mouseClickTable

    private void cbEstadoCLienteCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstadoCLienteCLIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstadoCLienteCLIActionPerformed

    private void txtTelefonoClienteCLIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteCLIKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTelefonoClienteCLIKeyTyped

    private void txtTelefonoClienteCLIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteCLIKeyReleased
        String Telefono = txtTelefonoClienteCLI.getText();
        if (Character.isDigit(evt.getKeyChar()) == true) {

        } else {
            Telefono = Telefono.substring(0, Telefono.length() - 1);

            txtTelefonoClienteCLI.setText(Telefono);
        }
    }//GEN-LAST:event_txtTelefonoClienteCLIKeyReleased

    private void txtDireccionClienteCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionClienteCLIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionClienteCLIActionPerformed

    private void txtCorreoClienteCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoClienteCLIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoClienteCLIActionPerformed

    private void txtIdClienteCLIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteCLIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteCLIActionPerformed

    private void txtCeliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCeliminarActionPerformed
        // TODO add your handling code here:
        ComprasDB data = new ComprasDB();
        if (tbnuevacompra.getValueAt(tbnuevacompra.getSelectedRow(), 4).toString() != "") {
            totalCompra -= Double.parseDouble(tbnuevacompra.getValueAt(tbnuevacompra.getSelectedRow(), 4).toString());
            data.reducirStock(tbnuevacompra.getValueAt(tbnuevacompra.getSelectedRow(), 0).toString(),
                    Integer.parseInt(tbnuevacompra.getValueAt(tbnuevacompra.getSelectedRow(), 2).toString()));

            lblcompraTotal.setText(totalCompra + "");
            DefaultTableModel dtm = (DefaultTableModel) tbnuevacompra.getModel();
            dtm.removeRow(tbnuevacompra.getSelectedRow());

        }

        if (txtCompraTipoPago.getSelectedItem() != "Tarjeta") {
            txtCCambio.setText((montoCompra - totalCompra) + "");
        }
        DefaultTableModel dtm = (DefaultTableModel) tbnuevacompra.getModel();
        dtm.removeRow(tbnuevacompra.getSelectedRow());

        listaProd.remove(listaProd.size() - 1);
    }//GEN-LAST:event_txtCeliminarActionPerformed
    double montoCompra = 0;
    private void btnAgregarProductoCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoCompraActionPerformed
        // TODO add your handling code here:
        VentasDB data = new VentasDB();
        datosProductoCompra dataProductos = new datosProductoCompra();
        int cantidad = Integer.parseInt(txtCcantidad.getText());

        if (txtCidProducto.getText().length() > 0 && txtCcantidad.getText().length() > 0) {

            try {
                int cant = Integer.parseInt(txtCcantidad.getText());

                modelVenta = (DefaultTableModel) tbnuevacompra.getModel();
                Object[] object = new Object[5];

                //
//                    dataProductos.setIdprod(txtIdProductoNV.getText());
//                    dataProductos.setDesc(txtDescripcionProductoNV.getText());
//                    dataProductos.setCant(cant + "");;
//                    dataProductos.setPrecio(txtPrecioNV.getText());
//                    dataProductos.setTotal(txtTotalVN.getText());
//                    System.out.println(dataProductos.getDesc());
                //
                data.plusStock(txtCidProducto.getText(),
                        Integer.parseInt(txtCcantidad.getText()));

                //
                boolean addProd = true;
                if (tbnuevacompra.getRowCount() > 0) {
                    for (int i = 0; i < tbnuevacompra.getRowCount(); i++) {
                        if (tbnuevacompra.getValueAt(i, 0).toString().equals(txtCidProducto.getText())) {
                            // Eliminar producto lista
                            listaProd.remove(i);
                            //
                            int cant1 = Integer.parseInt(tbnuevacompra.getValueAt(i, 2).toString());
                            int cant2 = cant;
                            double total1 = Double.parseDouble(tbnuevacompra.getValueAt(i, 4).toString()) + Double.parseDouble(txtCtotal.getText());

                            tbnuevacompra.setValueAt(cant + cant2, i, 2);
                            tbnuevacompra.setValueAt(total1, i, 4);
                            dataProductos.setIdprod(tbnuevacompra.getValueAt(i, 0).toString());
                            dataProductos.setDesc(tbnuevacompra.getValueAt(i, 1).toString());
                            dataProductos.setCant(tbnuevacompra.getValueAt(i, 2).toString());;
                            dataProductos.setPrecio(tbnuevacompra.getValueAt(i, 3).toString());
                            dataProductos.setTotal(tbnuevacompra.getValueAt(i, 4).toString());
                            System.out.println(tbnuevacompra.getValueAt(i, 0).toString() + tbnuevacompra.getValueAt(i, 1).toString() + tbnuevacompra.getValueAt(i, 2).toString());
                            listaProd.add(dataProductos);
                            System.out.println("elproducto esta en el indice :" + i);
                            addProd = false;
                            break;
                        }
                    }
                    if (addProd) {
                        object[0] = txtCidProducto.getText();
                        object[1] = txtCdescrip.getText();
                        object[2] = cant;
                        object[3] = txtCprecio.getText();
                        object[4] = txtCtotal.getText();
                        //
                        dataProductos.setIdprod(txtCidProducto.getText());
                        dataProductos.setDesc(txtCdescrip.getText());
                        dataProductos.setCant(cant + "");;
                        dataProductos.setPrecio(txtCprecio.getText());
                        dataProductos.setTotal(txtCtotal.getText());
                        System.out.println(dataProductos.getDesc());
                        modelVenta.addRow(object);
                        tbnuevacompra.setModel(modelVenta);
                        listaProd.add(dataProductos);
                    }

                } else {
                    object[0] = txtCidProducto.getText();
                    object[1] = txtCdescrip.getText();
                    object[2] = cant;
                    object[3] = txtCprecio.getText();
                    object[4] = txtCtotal.getText();
                    //
                    dataProductos.setIdprod(txtCidProducto.getText());
                    dataProductos.setDesc(txtCdescrip.getText());
                    dataProductos.setCant(cant + "");;
                    dataProductos.setPrecio(txtCprecio.getText());
                    dataProductos.setTotal(txtCtotal.getText());
                    //System.out.println(dataProductos.getDesc());
                    //
                    modelVenta.addRow(object);
                    tbnuevacompra.setModel(modelVenta);
                    listaProd.add(dataProductos);
                }

                //
                //
                totalCompra += Double.parseDouble(txtCtotal.getText());
                lblcompraTotal.setText(totalCompra + "");
                if (txtCompraTipoPago.getSelectedItem() != "Tarjeta") {
                    txtCCambio.setText((montoCompra - totalCompra) + "");
                }

                limpiarVenta();
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "La cantidad debe ser un entero", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hay vampos vacios", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarProductoCompraActionPerformed

    private void txtCprecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCprecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCprecioActionPerformed

    private void txtCcantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCcantidadKeyReleased
        // TODO add your handling code here:
        String cantidad = txtCcantidad.getText();
        if (Character.isDigit(evt.getKeyChar()) == true) {
            String id = txtCcantidad.getText();
            if ("0".equals(txtCcantidad.getText()) || txtCcantidad.getText().startsWith("-") || txtCcantidad.getText().startsWith(".")) {
                txtCcantidad.setText(null);
            } else if (id.contains(".")) {

                id = id.substring(0, id.length() - 1);

                txtCcantidad.setText(id);

            } else {
                double cant = Double.parseDouble(txtCcantidad.getText());
                double precio = Double.parseDouble(txtCprecio.getText());
                double total = cant * precio;
                txtCtotal.setText(total + "");

            }
        } else {
            cantidad = cantidad.substring(0, cantidad.length() - 1);

            txtCcantidad.setText(cantidad);
        }


    }//GEN-LAST:event_txtCcantidadKeyReleased

    private void txtCcantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCcantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCcantidadActionPerformed

    private void txtCcantidadMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCcantidadMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCcantidadMouseReleased

    private void txtCdescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCdescripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCdescripActionPerformed

    private void txtCidProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidProductoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidProductoKeyTyped

    private void txtCidProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidProductoKeyReleased
        // TODO add your handling code here:
        ConexionSQL cn = new ConexionSQL();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from `producto` WHERE idProducto=?";

        double cantidad;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            if (txtCidProducto.getText() != "") {
                ps.setInt(1, Integer.parseInt(txtCidProducto.getText()));
                rs = ps.executeQuery();
                if (rs.next()) {
                    txtCdescrip.setText(rs.getString("Nombre"));
                    txtCprecio.setText(rs.getString("precio"));
                } else {
                    txtCdescrip.setText(null);
                    txtCprecio.setText(null);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }//GEN-LAST:event_txtCidProductoKeyReleased

    private void txtCidProductoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidProductoVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidProductoVentaKeyPressed

    private void txtCidProductoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCidProductoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidProductoPropertyChange

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed

        limpiarTablaVentas();
        total = 0.00;
        lblTotal.setText(total + " L.");
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void txtIdClienteCLIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdClienteCLIKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtIdClienteCLIKeyReleased

    private void txtNombreClienteCLIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteCLIKeyReleased
        // TODO add your handling code here:
        String nombre = txtNombreClienteCLI.getText();
        if (Character.isDigit(evt.getKeyChar()) == false) {

            String id = txtNombreClienteCLI.getText();

            Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
            Matcher mat = pat.matcher(nombre);
            if (mat.find()) {
            } else {

                nombre = nombre.substring(0, nombre.length() - 1);

                txtNombreClienteCLI.setText(nombre);

            }
        } else {
            nombre = nombre.substring(0, nombre.length() - 1);

            txtNombreClienteCLI.setText(nombre);
        }

    }//GEN-LAST:event_txtNombreClienteCLIKeyReleased

    private void txtCorreoClienteCLIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoClienteCLIKeyReleased
        // TODO add your handling code here:
        String correo = txtCorreoClienteCLI.getText();

        Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
        Matcher mat = pat.matcher(correo);
        if (mat.find()) {
        } else {

            correo = correo.substring(0, correo.length() - 1);

            txtCorreoClienteCLI.setText(correo);

        }
    }//GEN-LAST:event_txtCorreoClienteCLIKeyReleased

    private void txtDireccionClienteCLIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteCLIKeyReleased
        // TODO add your handling code here:
        String direc = txtDireccionClienteCLI.getText();

        Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
        Matcher mat = pat.matcher(direc);
        if (mat.find()) {
        } else {

            direc = direc.substring(0, direc.length() - 1);

            txtDireccionClienteCLI.setText(direc);

        }
    }//GEN-LAST:event_txtDireccionClienteCLIKeyReleased

    private void tbhistorialventaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbhistorialventaFacturaMouseClicked
        // TODO add your handling code here:
        String id;
        id = tbhistorialventaFactura.getValueAt(tbhistorialventaFactura.getSelectedRow(), 0).toString();

        ///
        try {
            ConexionSQL con = new ConexionSQL();
            Connection conn = con.getConnection();
            Map paremetro = new HashMap();
            paremetro.put("Fecha", id);
            JasperReport reporte = null;
            String path = "src\\Layouts\\BusquedaFactura.jasper";

            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint jprint = JasperFillManager.fillReport(reporte, paremetro, conn);

            JasperViewer view = new JasperViewer(jprint, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

        } catch (Exception ex) {

        }
        //


    }//GEN-LAST:event_tbhistorialventaFacturaMouseClicked

    private void btnsearfacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearfacturaActionPerformed
        // TODO add your handling code here:
        if (jfecha1.getDate() != null && jfecha2.getDate() != null) {

            limpiarTablaVentasFacturas();
            Date fecha1 = (jfecha1.getDate());
            Date fecha2 = (jfecha2.getDate());
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            String fechatxt1 = f.format(fecha1);
            String fechatxt2 = f.format(fecha2);

            VentasDB cliente = new VentasDB();
            List<VentasFacturacionHistorial> listaCl = cliente.ListarHistorialVentaFacturaFecha(fechatxt1, fechatxt2);
            modelHistorialVentaFactura = (DefaultTableModel) tbhistorialventaFactura.getModel();

            Object[] obj = new Object[6];
            for (int i = 0; i < listaCl.size(); i++) {
                obj[0] = listaCl.get(i).getIdFactura();
                obj[1] = listaCl.get(i).getFecha();
                obj[2] = listaCl.get(i).getEmpleado();
                obj[3] = listaCl.get(i).getCliente();

                modelHistorialVentaFactura.addRow(obj);

            }
            tbhistorialventaFactura.setModel(modelHistorialVentaFactura);

        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto \n"
                    + "Ejemplo: ene 1, 2021 \n"
                    + "O dar clic al icono del calendario",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnsearfacturaActionPerformed

    private void btnrefreshfacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshfacturaActionPerformed
        // TODO add your handling code here:
        limpiarTablaVentasFacturas();
        listaHistorialVentaFacutra();
    }//GEN-LAST:event_btnrefreshfacturaActionPerformed
    int idPrecioHistoricto;
    private void tbPHproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPHproductoMouseClicked
        // TODO add your handling code here:
        idPrecioHistoricto = Integer.parseInt(tbPHproducto.getValueAt(tbPHproducto.getSelectedRow(), 0).toString());
        LimpiarTablaPrecioHistorico();
        listarPrecios(idPrecioHistoricto);
    }//GEN-LAST:event_tbPHproductoMouseClicked

    private void txtidProveedorCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidProveedorCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidProveedorCompraActionPerformed

    private void txtidProveedorCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidProveedorCompraKeyReleased
        // TODO add your handling code here:
        DatosCliente cliente = new DatosCliente();
        ConexionSQL cn = new ConexionSQL();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from `proveedor` WHERE idProveedor=?";

        double cantidad;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            if (txtidProveedorCompra.getText() != "") {
                ps.setInt(1, Integer.parseInt(txtidProveedorCompra.getText()));
                rs = ps.executeQuery();
                if (rs.next()) {
                    txtCompraNombreProveedor.setText(rs.getString("Nombre"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setTelefono(rs.getString("TelefonoPro"));

                } else {
                    txtCompraNombreProveedor.setText(null);

                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }//GEN-LAST:event_txtidProveedorCompraKeyReleased
    public static String Operacion = "";

    public String getOperacion() {
        return Operacion;
    }

    public void setOperacion(String Operacion) {
        Sistema.Operacion = Operacion;
    }
    private void btnCfacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCfacturarActionPerformed
        // TODO add your handling code here:
        Operacion = "compra";
        valoresEstaticos vals = new valoresEstaticos();
//        if (txtidProveedorCompra.getText().length() > 0 && txtCompraNombreProveedor.getText().length() > 2) {
//
//            try {
//
//                String str = lblcompraTotal.getText();
//                str = str.substring(0, str.length() - 1);
//                str = str.substring(0, str.length() - 1);
//
//                ComprasDB data = new ComprasDB();
//
//                data.RegistrarCompra(vals.getIdUsuario(), Integer.parseInt(txtidProveedorCompra.getText()),
//                        txtCompraTipoPago.getSelectedIndex() + 1, totalCompra);
//
//                limpiarCompra();
//                LimpiarTablaCompra();
//                txtidProveedorCompra.setText(null);
//                txtCompraNombreProveedor.setText(null);
//                limpiarTablaProducto();
//                listaProductos();
//                lblcompraTotal.setText("0.00");
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//
//        } else {
//            JOptionPane.showMessageDialog(null, "El campo proveedor esta vacio", "Error", JOptionPane.WARNING_MESSAGE);
//
//        }
        /////////////////////////////////////////////
        datosProductoCompra dataProductos = new datosProductoCompra();
//                for (int i = 0; i < tbNuevaVenta.getRowCount(); i++) {
//                    
//                        dataProductos.setIdprod(tbNuevaVenta.getValueAt(i, 0).toString());
//                        dataProductos.setDesc(tbNuevaVenta.getValueAt(i, 1).toString());
//                        dataProductos.setCant(tbNuevaVenta.getValueAt(i, 2).toString());;
//                        dataProductos.setPrecio(tbNuevaVenta.getValueAt(i, 3).toString());
//                        dataProductos.setTotal(tbNuevaVenta.getValueAt(i, 4).toString());
//                        System.out.println(tbNuevaVenta.getValueAt(i, 0).toString()+tbNuevaVenta.getValueAt(i, 1).toString()+tbNuevaVenta.getValueAt(i, 2).toString());
//                        
//                        
//                        listaProd.add(dataProductos);  
//                }    
        //

        if (txtidProveedorCompra.getText().length() > 0 && txtCompraNombreProveedor.getText().length() > 2) {

            try {
                //valoresEstaticos vals = new valoresEstaticos();
                Ventas vnt = new Ventas();

                DatosCliente cliente = new DatosCliente();

                cliente.setCliente(txtCompraNombreProveedor.getText());

                cliente.setIdCliente(Integer.parseInt(txtidProveedorCompra.getText()));
                cliente.setEmpleado(vals.getIdUsuario() + "");
                String str = lblcompraTotal.getText();
                str = str.substring(0, str.length() - 1);
                str = str.substring(0, str.length() - 1);
                cliente.setTotal(Double.parseDouble(str));

                vnt.setCliente(txtCompraNombreProveedor.getText());
                vnt.setIdCliente(Integer.parseInt(txtidProveedorCompra.getText()));
                vnt.setEmpleado(vals.getUsuario());
                vnt.setIdEmpleado(vals.getIdUsuario());
                vnt.setTotal(Double.parseDouble(lblcompraTotal.getText()));
                vnt.setCambio(Double.parseDouble(txtCCambio.getText()));

                ComprasDB data = new ComprasDB();
                if (txtCompraTipoPago.getSelectedItem() == "Tarjeta") {
                    txtCCambio.setText("0.00");
                    txtCMontoPagar.setText("0.00");
                    data.RegistrarCompra(vnt, txtCompraTipoPago.getSelectedIndex() + 1, "Tarj");
                    new DetalleFactura().setVisible(true);
                    listaProd = new ArrayList();
                    totalCompra = 0;
                    txtCMontoPagar.setText("0.00");
                    txtCCambio.setText("0.00");
                    limpiarCompra();
                    LimpiarTablaCompra();
                } else if (txtCompraTipoPago.getSelectedItem() == "Mixto") {
                    if (Double.parseDouble(txtCMontoPagar.getText()) >= Double.parseDouble(lblcompraTotal.getText()) || Double.parseDouble(txtCMontoPagar.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null, "Para el pago mixto\nel monto debe ser menor que el total", "Error", JOptionPane.WARNING_MESSAGE);

                    } else {

                        double efectivo = Double.parseDouble(txtCMontoPagar.getText());
                        double tarjeta = totalCompra - efectivo;
                        JOptionPane.showMessageDialog(null, "Registro de venta exitoso\nPago en efectivo: L" + efectivo + "\nPago acreditado a tarjeta: L" + tarjeta);
                        data.RegistrarCompra(vnt, txtCompraTipoPago.getSelectedIndex() + 1, "Mixto");
                        new DetalleFactura().setVisible(true);
                        listaProd = new ArrayList();
                        totalCompra = 0;
                        txtCMontoPagar.setText("0.00");
                        txtCCambio.setText("0.00");
                        limpiarCompra();
                        LimpiarTablaCompra();

                    }
                } else if (txtCompraTipoPago.getSelectedItem() == "Efectivo") {
                    if (Double.parseDouble(txtCCambio.getText()) < 0) {
                        JOptionPane.showMessageDialog(null, "Monto a pagar invalido", "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        data.RegistrarCompra(vnt, txtCompraTipoPago.getSelectedIndex() + 1, "efe");
                        new DetalleFactura().setVisible(true);
                        listaProd = new ArrayList();
                        totalCompra = 0;
                        txtCMontoPagar.setText("0.00");
                        txtCCambio.setText("0.00");
                        limpiarCompra();
                        LimpiarTablaCompra();

                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "El campo cliente esta vacio", "Error", JOptionPane.WARNING_MESSAGE);

        }
        limpiarTablaProducto();
        listaProductos();


    }//GEN-LAST:event_btnCfacturarActionPerformed

    private void btnCrefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrefrescarActionPerformed
        // TODO add your handling code here:
        limpiarCompra();
        LimpiarTablaCompra();
    }//GEN-LAST:event_btnCrefrescarActionPerformed
    String datePrecioHistorico;
    private void tbPHhistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPHhistorialMouseClicked
        // TODO add your handling code here:

        datePrecioHistorico = tbPHhistorial.getValueAt(tbPHhistorial.getSelectedRow(), 0).toString();
        String[] partes = datePrecioHistorico.split(">");
        String cpart1 = partes[0];
        String cpart2 = partes[1];

        //convertir nombre
        String fecha1 = cpart1;
        String fecha2 = cpart2;
        LimpiarTablaPrecioHistoricoMov();
        listarPreciosMovimiento(fecha1, idPrecioHistoricto);
    }//GEN-LAST:event_tbPHhistorialMouseClicked

    private void txtprecioProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioProdKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtprecioProdKeyTyped

    private void txtprecioProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioProdKeyReleased
        // TODO add your handling code here:
        if ("0".equals(txtprecioProd.getText()) || txtprecioProd.getText().startsWith("-") || txtprecioProd.getText().startsWith("0") || txtprecioProd.getText().startsWith(".")) {
            txtprecioProd.setText(null);
        } else {
            String string = txtprecioProd.getText();
            if (string.contains(".")) {
                System.out.println("encontre un decial");
                String[] parts = string.split(".");

                String[] arr = String.valueOf(string).split("\\.");
                int[] intArr = new int[2];
                intArr[0] = Integer.parseInt(arr[0]); // 1
                intArr[1] = Integer.parseInt(arr[1]); // 9

//              
                String decimal = String.valueOf(intArr[1]);
                if (decimal.length() > 2) {
                    System.out.println("Decimal mayor a 2:" + intArr[0] + "." + intArr[1]);
                    string = string.substring(0, string.length() - 1);

                    txtprecioProd.setText(string);
                }
            }

            //convertir nombre
        }
        String precio = txtprecioProd.getText();
        if (Character.isDigit(evt.getKeyChar()) == true) {

        } else {
            precio = precio.substring(0, precio.length() - 1);

            txtprecioProd.setText(precio);
        }


    }//GEN-LAST:event_txtprecioProdKeyReleased

    private void txtStockProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockProdKeyReleased
        // TODO add your handling code here:
        String id = txtStockProd.getText();
        if ("0".equals(txtStockProd.getText()) || txtStockProd.getText().startsWith("-") || txtStockProd.getText().startsWith(".")) {
            txtStockProd.setText(null);
        } else if (id.contains(".")) {

            id = id.substring(0, id.length() - 1);

            txtStockProd.setText(id);

        }
        String stock = txtStockProd.getText();
        if (Character.isDigit(evt.getKeyChar()) == true) {

        } else {
            stock = stock.substring(0, stock.length() - 1);

            txtStockProd.setText(stock);
        }


    }//GEN-LAST:event_txtStockProdKeyReleased

    private void txtNombreClienteCLIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteCLIKeyTyped
        // TODO add your handling code here:
        if (txtNombreClienteCLI.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreClienteCLIKeyTyped

    private void txtDireccionClienteCLIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteCLIKeyTyped
        // TODO add your handling code here:
        if (txtDireccionClienteCLI.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDireccionClienteCLIKeyTyped

    private void txtNombreProveedoresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedoresKeyTyped
        // TODO add your handling code here:
        if (txtNombreProveedores.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreProveedoresKeyTyped

    private void txtubicacionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtubicacionProveedorKeyTyped
        // TODO add your handling code here:
        if (txtubicacionProveedor.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtubicacionProveedorKeyTyped

    private void txtNombreEmpleadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEmpleadosKeyTyped
        // TODO add your handling code here:
        if (txtNombreEmpleados.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreEmpleadosKeyTyped

    private void txtDireccionEmpleadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionEmpleadosKeyTyped
        // TODO add your handling code here:
        if (txtDireccionEmpleados.getText().length() == 30) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDireccionEmpleadosKeyTyped

    private void btnNuevaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevaVentaMouseClicked
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(6);
    }//GEN-LAST:event_btnNuevaVentaMouseClicked

    private void btnProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedoresMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnProveedoresMouseClicked

    private void btnEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnEmpleadosMouseClicked

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnVentasMouseClicked

    private void txtNombreProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProveedoresActionPerformed

    private void txtNombreProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedoresKeyReleased
        // TODO add your handling code here:
        String nombre = txtNombreProveedores.getText();
        if (Character.isDigit(evt.getKeyChar()) == false) {

            String id = txtNombreProveedores.getText();

            Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
            Matcher mat = pat.matcher(nombre);
            if (mat.find()) {
            } else {

                nombre = nombre.substring(0, nombre.length() - 1);

                txtNombreProveedores.setText(nombre);

            }
        } else {
            nombre = nombre.substring(0, nombre.length() - 1);

            txtNombreProveedores.setText(nombre);
        }

    }//GEN-LAST:event_txtNombreProveedoresKeyReleased

    private void txtNombreEmpleadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEmpleadosKeyReleased
        // TODO add your handling code here:
        String nombre = txtNombreEmpleados.getText();
        if (Character.isDigit(evt.getKeyChar()) == false) {

            String id = txtNombreEmpleados.getText();

            Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
            Matcher mat = pat.matcher(nombre);
            if (mat.find()) {
            } else {

                nombre = nombre.substring(0, nombre.length() - 1);

                txtNombreEmpleados.setText(nombre);

            }
        } else {
            nombre = nombre.substring(0, nombre.length() - 1);

            txtNombreEmpleados.setText(nombre);
        }


    }//GEN-LAST:event_txtNombreEmpleadosKeyReleased

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(5);
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtCorreoProveedoresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoProveedoresKeyPressed
        // TODO add your handling code here:
        String correo = txtCorreoProveedores.getText();

        Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
        Matcher mat = pat.matcher(correo);
        if (mat.find()) {
        } else {

            correo = correo.substring(0, correo.length() - 1);

            txtCorreoProveedores.setText(correo);

        }
    }//GEN-LAST:event_txtCorreoProveedoresKeyPressed

    private void txtubicacionProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtubicacionProveedorKeyReleased
        // TODO add your handling code here:
        String direc = txtubicacionProveedor.getText();

        Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
        Matcher mat = pat.matcher(direc);
        if (mat.find()) {
        } else {

            direc = direc.substring(0, direc.length() - 1);

            txtubicacionProveedor.setText(direc);

        }
    }//GEN-LAST:event_txtubicacionProveedorKeyReleased

    private void txtCorreoEmpleadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoEmpleadosKeyPressed
        // TODO add your handling code here:
        String correo = txtCorreoEmpleados.getText();

        Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
        Matcher mat = pat.matcher(correo);
        if (mat.find()) {
        } else {

            correo = correo.substring(0, correo.length() - 1);

            txtCorreoEmpleados.setText(correo);

        }
    }//GEN-LAST:event_txtCorreoEmpleadosKeyPressed

    private void txtDireccionEmpleadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionEmpleadosKeyReleased
        // TODO add your handling code here:
        String direc = txtDireccionEmpleados.getText();

        Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
        Matcher mat = pat.matcher(direc);
        if (mat.find()) {
        } else {

            direc = direc.substring(0, direc.length() - 1);

            txtDireccionEmpleados.setText(direc);

        }

    }//GEN-LAST:event_txtDireccionEmpleadosKeyReleased

    private void refreshProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshProveedoresActionPerformed
        // TODO add your handling code here:
        limpiarTablaProveedor();
        listaProveedores();
    }//GEN-LAST:event_refreshProveedoresActionPerformed

    private void txtCprecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCprecioKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCprecioKeyReleased

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(2);
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(8);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnsearchventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchventaActionPerformed
        // TODO add your handling code here:

        if (txtcodventa.getText() != "") {
            String TipoFiltro = cmbHventa.getSelectedItem().toString();
            System.out.println(TipoFiltro);
            //limpiarTabla();
            VentasDB Hventas = new VentasDB();
            //ClienteDB cl = new ClienteDB();
            List<Ventas> listaCl = null;
            String state = null;
            switch (TipoFiltro) {
                case "Cliente":

                    listaCl = Hventas.listarVentaBusqueda("Cliente", txtcodventa.getText());
                    state = "true";
                    break;

                case "ID Venta":
                    int id;
                    try {

                        id = Integer.parseInt(txtcodventa.getText());
                        listaCl = Hventas.listarVentaBusqueda("idFactura", Integer.toString(id));
                        state = "true";
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "ID es un valor numerico", "Error", JOptionPane.WARNING_MESSAGE);
                        listaClientes();
                    }

                    break;

                case "Vendedor":

                    listaCl = Hventas.listarVentaBusqueda("Vendedor", txtcodventa.getText());
                    state = "true";
                    break;

            }
            if (state == "true") {
                limpiarTablaHistorialV();
                VentasDB cliente = new VentasDB();
                modelHistorialVenta = (DefaultTableModel) tbVentas.getModel();

                Object[] obj = new Object[6];
                for (int i = 0; i < listaCl.size(); i++) {
                    obj[0] = listaCl.get(i).getIdFactura();
                    obj[1] = listaCl.get(i).getCliente();
                    obj[2] = listaCl.get(i).getEmpleado();
                    obj[3] = listaCl.get(i).getTotal();
                    modelHistorialVenta.addRow(obj);

                }
                tbVentas.setModel(modelHistorialVenta);

                ///
            }

        } else {
            JOptionPane.showMessageDialog(null, "Hay campos vacios");
        }
        ///


    }//GEN-LAST:event_btnsearchventaActionPerformed

    private void btnventarefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventarefreshActionPerformed
        // TODO add your handling code here:
        limpiarTablaHistorialV();
        listaHistorialVenta();
    }//GEN-LAST:event_btnventarefreshActionPerformed

    private void btnsalirsistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirsistemaActionPerformed
        // TODO add your handling code here:
        //Confirmar salida

        System.exit(0);
    }//GEN-LAST:event_btnsalirsistemaActionPerformed

    private void txtTelefonoProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedoresKeyReleased
        // TODO add your handling code here:
        String Telefono = txtTelefonoProveedores.getText();
        if (Character.isDigit(evt.getKeyChar()) == true) {

        } else {
            Telefono = Telefono.substring(0, Telefono.length() - 1);

            txtTelefonoProveedores.setText(Telefono);
        }
    }//GEN-LAST:event_txtTelefonoProveedoresKeyReleased

    private void txtTelefonoEmpleadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoEmpleadosKeyReleased
        // TODO add your handling code here:
        String Telefono = txtTelefonoEmpleados.getText();
        if (Character.isDigit(evt.getKeyChar()) == true) {

        } else {
            Telefono = Telefono.substring(0, Telefono.length() - 1);

            txtTelefonoEmpleados.setText(Telefono);
        }
    }//GEN-LAST:event_txtTelefonoEmpleadosKeyReleased

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        // TODO add your handling code here:
        ConfiguracionEmpresa cfg = new ConfiguracionEmpresa();
        cfg.setVisible(true);
    }//GEN-LAST:event_btnEmpresaActionPerformed
    String StadoPago = "";
    private void cmbtipopagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbtipopagoItemStateChanged
        // TODO add your handling code here:

        if (cmbtipopago.getSelectedItem() == "Tarjeta") {

            txtMontoPagar.setEnabled(false);
            txtMontoPagar.setText("0.00");
            txtCambio.setText("0.00");
        } else if (cmbtipopago.getSelectedItem() == "Mixto") {
            txtMontoPagar.setEnabled(true);
            txtCambio.setText("0.00");
        } else {
            txtMontoPagar.setEnabled(true);
            txtCambio.setText((0 - total) + "");
        }
    }//GEN-LAST:event_cmbtipopagoItemStateChanged

    private void txtMontoPagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMontoPagarMouseClicked
        // TODO add your handling code here:
        txtMontoPagar.setText(null);
    }//GEN-LAST:event_txtMontoPagarMouseClicked
    double monto = 0;
    private void txtMontoPagarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoPagarKeyReleased
        // TODO add your handling code here:
        String Monto = txtCMontoPagar.getText();

        if (txtCMontoPagar.getText().startsWith("-") || txtCMontoPagar.getText().startsWith(".") || txtCMontoPagar.getText().startsWith("0")) {
            txtCMontoPagar.setText(null);
        } else if (Character.isDigit(evt.getKeyChar()) == true) {
            if (txtCompraTipoPago.getSelectedItem() == "Tarjeta") {

                txtCMontoPagar.setEnabled(false);
                txtCMontoPagar.setText("0.00");
                txtCCambio.setText("0.00");
            } else if (txtCompraTipoPago.getSelectedItem() == "Mixto") {
                txtCMontoPagar.setEnabled(true);
                txtCCambio.setText("0.00");
            } else {
                montoCompra = Double.parseDouble(txtCMontoPagar.getText());
                txtCCambio.setText((montoCompra - totalCompra) + "");
            }

        } else {
            Monto = Monto.substring(0, Monto.length() - 1);

            txtCMontoPagar.setText(Monto);
        }
    }//GEN-LAST:event_txtMontoPagarKeyReleased

    private void cmbTipoFiltroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoFiltroClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoFiltroClienteActionPerformed

    private void txtNombreDelProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreDelProductoKeyReleased
        String nombre = txtNombreDelProducto.getText();
        if (Character.isDigit(evt.getKeyChar()) == false) {

            String id = txtNombreDelProducto.getText();

            Pattern pat = Pattern.compile("^(?!.*(.)\\1{2})");
            Matcher mat = pat.matcher(nombre);
            if (mat.find()) {
            } else {

                nombre = nombre.substring(0, nombre.length() - 1);

                txtNombreDelProducto.setText(nombre);

            }
        } else {
            nombre = nombre.substring(0, nombre.length() - 1);

            txtNombreDelProducto.setText(nombre);
        }

    }//GEN-LAST:event_txtNombreDelProductoKeyReleased

    private void txtCMontoPagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCMontoPagarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCMontoPagarMouseClicked

    private void txtCMontoPagarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCMontoPagarKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCMontoPagarKeyReleased

    private void txtCompraTipoPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtCompraTipoPagoItemStateChanged
        // TODO add your handling code here:
        if (txtCompraTipoPago.getSelectedItem() == "Tarjeta") {

            txtCMontoPagar.setEnabled(false);
            txtCMontoPagar.setText("0.00");
            txtCCambio.setText("0.00");
        } else if (txtCompraTipoPago.getSelectedItem() == "Mixto") {
            txtCMontoPagar.setEnabled(true);
            txtCCambio.setText("0.00");
        } else {
            txtCMontoPagar.setEnabled(true);
            txtCCambio.setText((0 - totalCompra) + "");
        }
    }//GEN-LAST:event_txtCompraTipoPagoItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        panelInfo.setSelectedIndex(7);
    }//GEN-LAST:event_jButton1ActionPerformed
    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public void ActiveProducto() {

        txtNombreDelProducto.setEnabled(true);
        txtprecioProd.setEnabled(true);
        txtStockProd.setEnabled(true);

    }

    public void inActivProducto() {
        txtIdProductosProductos.setEnabled(false);
        txtNombreDelProducto.setEnabled(false);
        txtprecioProd.setEnabled(false);
        txtStockProd.setEnabled(false);
    }

    public void LimpiarProducto() {
        txtIdProductosProductos.setText(null);
        txtNombreDelProducto.setText(null);
        txtprecioProd.setText(null);
        txtStockProd.setText(null);
        //

    }

    public void CancelProcess() {
        tbClientesCLI.setEnabled(true);
        txtIdClienteCLI.setEnabled(false);
        txtNombreClienteCLI.setEnabled(false);
        btnGuardarCLI.setEnabled(false);
        btnNuevoClienteCLI.setEnabled(true);
        btnModificarCLI.setEnabled(true);
        txtCorreoClienteCLI.setEnabled(false);
        txtDireccionClienteCLI.setEnabled(false);
        txtTelefonoClienteCLI.setEnabled(false);
        cbEstadoCLienteCLI.setEnabled(false);
        LimpiarTextfield();
        btncancel.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    private void cargarMeses() {

        meses[1] = "Enero";
        meses[2] = "Febrero";
        meses[3] = "Marzo";
        meses[4] = "Abril";
        meses[5] = "Junio";
        meses[6] = "Julio";
        meses[7] = "Agosto";
        meses[8] = "Septiembre";
        meses[9] = "Octubre";
        meses[10] = "Noviembre";
        meses[11] = "Diciembre";

    }

    public int getPrivilegioUser() {
        return PrivilegioUser;
    }

    public void setPrivilegioUser(int PrivilegioUser) {
        this.PrivilegioUser = PrivilegioUser;
    }
    public static int PrivilegioUser;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClienteBuscar;
    private javax.swing.JButton ClienteBuscar1;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnAgregarNV;
    private javax.swing.JButton btnAgregarProductoCompra;
    private javax.swing.JButton btnAgregarProductos;
    private javax.swing.JButton btnCancelEmp;
    private javax.swing.JButton btnCfacturar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCrefrescar;
    private javax.swing.JButton btnEliminarNV;
    private javax.swing.JButton btnEliminarNV2;
    private javax.swing.JButton btnEliminarProductos;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnGuardarCLI;
    private javax.swing.JButton btnGuardarEmpleados;
    private javax.swing.JButton btnGuardarProveedores;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnModificarCLI;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JButton btnModificarProveedores;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoClienteCLI;
    private javax.swing.JButton btnNuevoEmpleado;
    private javax.swing.JButton btnNuevoProveedores;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProdCancelar;
    private javax.swing.JButton btnProdModificar;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnVentaNV;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btncanelProveedor;
    private javax.swing.JButton btnrefreshfactura;
    private javax.swing.JButton btnsalirsistema;
    private javax.swing.JButton btnsearchventa;
    private javax.swing.JButton btnsearfactura;
    private javax.swing.JButton btnventarefresh;
    private javax.swing.JComboBox<String> cbEstadoCLienteCLI;
    private javax.swing.JComboBox<String> cbEstadoEmpleados1;
    private javax.swing.JComboBox<String> cbEstadoProveedores;
    private javax.swing.JComboBox<String> cbOcupacionEmpleado;
    private javax.swing.JComboBox<String> cmbHventa;
    private javax.swing.JComboBox<String> cmbTipoFiltroCliente;
    private javax.swing.JComboBox<String> cmbempleado;
    private javax.swing.JComboBox<String> cmbproveedor;
    private javax.swing.JComboBox<String> cmbtipopago;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private com.toedter.calendar.JDateChooser jfecha1;
    private com.toedter.calendar.JDateChooser jfecha2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblcompraTotal;
    private javax.swing.JTabbedPane panelInfo;
    private javax.swing.JPanel pnlCompras;
    private javax.swing.JButton refreshProveedores;
    private javax.swing.JPanel tabClientes;
    private javax.swing.JPanel tabEmpleados;
    private javax.swing.JPanel tabHistorial;
    private javax.swing.JPanel tabNuevaVenta;
    private javax.swing.JPanel tabProductos;
    private javax.swing.JPanel tabProveedores;
    private javax.swing.JTable tbClientesCLI;
    private javax.swing.JTable tbEmpleados;
    private javax.swing.JTable tbNuevaVenta;
    private javax.swing.JTable tbPHhistorial;
    private javax.swing.JTable tbPHhistorialprecio;
    private javax.swing.JTable tbPHproducto;
    private javax.swing.JTable tbProductos;
    private javax.swing.JTable tbProveedores;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTable tbhistorialventaFactura;
    private javax.swing.JTable tbnuevacompra;
    private javax.swing.JTextField txtCCambio;
    private javax.swing.JTextField txtCMontoPagar;
    private javax.swing.JTextField txtCambio;
    private javax.swing.JTextField txtCantidadNV;
    private javax.swing.JTextField txtCcantidad;
    private javax.swing.JTextField txtCdescrip;
    private javax.swing.JButton txtCeliminar;
    private javax.swing.JTextField txtCidProducto;
    private javax.swing.JTextField txtCompraNombreProveedor;
    private javax.swing.JComboBox<String> txtCompraTipoPago;
    private javax.swing.JTextField txtCorreoClienteCLI;
    private javax.swing.JTextField txtCorreoEmpleados;
    private javax.swing.JTextField txtCorreoProveedores;
    private javax.swing.JTextField txtCprecio;
    private javax.swing.JTextField txtCtotal;
    private javax.swing.JTextField txtDescripcionProductoNV;
    private javax.swing.JTextField txtDireccionClienteCLI;
    private javax.swing.JTextField txtDireccionEmpleados;
    private javax.swing.JTextField txtIdClienteCLI;
    private javax.swing.JTextField txtIdClienteNV;
    private javax.swing.JTextField txtIdEmpleados;
    private javax.swing.JTextField txtIdProductoNV;
    private javax.swing.JTextField txtIdProductosProductos;
    private javax.swing.JTextField txtIdProveedores;
    private javax.swing.JTextField txtMontoPagar;
    private javax.swing.JTextField txtNombreClienteCLI;
    private javax.swing.JTextField txtNombreDelProducto;
    private javax.swing.JTextField txtNombreEmpleados;
    private javax.swing.JTextField txtNombreProveedores;
    private javax.swing.JTextField txtPrecioNV;
    private javax.swing.JTextField txtStockProd;
    private javax.swing.JTextField txtTelefonoClienteCLI;
    private javax.swing.JTextField txtTelefonoEmpleados;
    private javax.swing.JTextField txtTelefonoProveedores;
    private javax.swing.JTextField txtTotalVN;
    private javax.swing.JTextField txtarroba;
    private javax.swing.JTextField txtcodventa;
    private javax.swing.JTextField txtidProveedorCompra;
    private javax.swing.JTextField txtprecioProd;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txtsearchempleado;
    private javax.swing.JTextField txtsearchproovedor;
    private javax.swing.JTextField txtubicacionProveedor;
    // End of variables declaration//GEN-END:variables

    public JTable getTbNuevaVenta() {
        return tbNuevaVenta;
    }

    public void setTbNuevaVenta(JTable tbNuevaVenta) {
        this.tbNuevaVenta = tbNuevaVenta;
    }
}
