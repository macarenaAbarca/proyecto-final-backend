package cl.forge.programatufuturo.reservadehoras.controllers;


import cl.forge.programatufuturo.reservadehoras.models.Cliente;
import cl.forge.programatufuturo.reservadehoras.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private ClienteService clienteService;


    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }


    //Registro de cliente
    @GetMapping("/registrarcliente")
    public void registrarCliente(@RequestParam String rut,
                                     @RequestParam String nombre,
                                        @RequestParam String apellido,
                                            @RequestParam Integer telefono,
                                                @RequestParam String email,
                                                    @RequestParam String password){
        if (clienteService.existeRut(rut)){
            System.out.println("Este rut se encuentra registrado");
        }
        else if(clienteService.existeEmail(email)){
            System.out.println("El mail ya esta en uso");
        }
        else {
            Cliente cliente=new Cliente(rut,nombre,apellido,telefono,email,password);
            clienteService.registrarCliente(cliente);
            System.out.println("Guardado correctamente");
        }
    }

    //Login de cliente
    @GetMapping("/login")
    public boolean login(@RequestParam String rut, @RequestParam String password){
        String newPass=clienteService.encriptar(password);
        List<Cliente> cliente=clienteService.validador(rut,newPass);
        if(cliente.size()!=0){
            cliente.get(0).setUltimoLoginFecha(new Date());
            clienteService.modificarFecha(cliente.get(0));
            System.out.println("Bienvenido");
            return true;
        }
        else {
            System.out.println("Rut o clave incorrectos");
            return false;
        }
    }

    //@GetMapping("/reservarhora")


}
