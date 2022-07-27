package br.com.springboot.projetoJDev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.projetoJDev.model.Usuario;
import br.com.springboot.projetoJDev.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
		
    /**
     *
     * @param nome the name to greet
     * @return greeting text
     */
//    @RequestMapping(value = "/{nome}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public String greetingText(@PathVariable String nome) {
//        return "Api SpringBoot " + nome + "!";
//    }
    
    //Cria objeto
    @RequestMapping(value = "/retorno/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String metodoRetorna(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario);
    	
    	return "Ola mundo " + nome + " !!";    	
    }
   //Lista os objetos
    @GetMapping(value = "listatodos")//Primeiro metodo de API
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> listaUsuarios(){
    	List<Usuario> usuarios = usuarioRepository.findAll();/*Consulta no banco de dados*/    	
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/*retorna a lista em jason*/
    }
    //Salva objeto no banco
   @PostMapping(value = "salvar")/*Mapeia a URL*/
    @ResponseBody/*Descrição da resposta*/
    public ResponseEntity<Usuario>salvar(@RequestBody Usuario usuario){/*Recebe os dados para salver*/
    	
	  Usuario user = usuarioRepository.save(usuario);
	  return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	   
    }
   
   //Delete por id
   @DeleteMapping(value = "delete")
   @ResponseBody
   public ResponseEntity<String> delete(@RequestParam Long id){
	 
	   usuarioRepository.deleteById(id);
	  return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.OK );
   }
   
   
   //Atualiza os dados
   @PutMapping(value = "atualizar")
   @ResponseBody
   public ResponseEntity<?>atualizar(@RequestBody Usuario usuario){
	   if(usuario.getId() == null) {
		   return new ResponseEntity<String>("Id nao informado, não sera atualizado !", HttpStatus.OK);	   
	   }
	   Usuario user = usuarioRepository.saveAndFlush(usuario);
	   
	   return new ResponseEntity<Usuario>(user, HttpStatus.OK);	   
   }
   //Busca por nome
   @GetMapping(value = "buscarPorNome")
   @ResponseBody
   public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String name){
	   List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());//retira espaço e maiusculo
	   
	   return new ResponseEntity<List<Usuario>>(usuario,HttpStatus.OK);
   }
   
   //Busca por ID
   @GetMapping(value = "buscarId")
   @ResponseBody
   public ResponseEntity<Usuario>buscaId(@RequestParam(name = "id") Long id){
	 Usuario usuario =  usuarioRepository.findById(id).get();
		     
	   return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	  
   }
   
}
