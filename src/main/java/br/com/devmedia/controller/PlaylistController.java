package br.com.devmedia.controller;

import br.com.devmedia.domain.Playlist;
import br.com.devmedia.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("playlists")
public class PlaylistController {

	// O controller precisa acessar service para manipular a playlist e tem acesso as regras de negocio relacionada a esse negócio
	// @Autowired faz injeção desta dependencia quando o controller for iniciado
    @Autowired
    private PlaylistService playlistService;

    	// @GetMapping indica ao Spring MVC que requisições HTTP do tipo GET enviadas para localhost:8080/playlist/listar dever ser atendidas por este metodo 
    // O tipo de retorno ModelAndView
    // O parametro objeto do tipo ModelMap representa a implementação de um Map feita pelo Spring para que possa enviar dados para view
    // para enviar esses dados chamar o metodo addAttribute adicionar o atributo que deseja enviar para view nesse exemplo está nomeando como playlists e no segundo parametro passa o valor, isto é lista de playlist cadastradas 
    // então para devolver esses dados indicar a pagina que aplicação deve exibir criou um objeto ModelAndView nas instanciação passou como parametro o model o qual adicionou o atributo e uma string que indica pagina a ser exibida
    // a partir desse retorno dessa ModelAndView o front controller do Spring MVC repassara essas informações para view que processara a pagina list.html com dados enviados no model e rederizara o html o devolvendo para o front controller enviar a resposta para o browser
    
    @GetMapping("/listar")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("playlists", playlistService.recuperar());
        return new ModelAndView("/playlist/list", model);
    }

    // recebe um objeto do tipo Playlist como parametro, anotação @ModelAttribute faz o bind, isto é ligação entre objeto playList manipulado no formulario de cadastro e o objeto playlist esperado pelo controller
    @GetMapping("/cadastro")
    public String preSalvar(@ModelAttribute("playlist") Playlist playlist) {
    	// usario será redicionado para pagina de criação de playlist
        return "/playlist/add";
    }

    // @PostMapping verbo usado para criar 
    // o primeiro parametro tem o mesmo funcionamento do parametro explicado do metodo anterior, dessa forma que está recebendo da pagina de ediçao, objeto playlist com nome e descricao preenchidos
    // na classe PlayList foi adiocionadas algumas validações com essa anotação @Valid esta indicando que essas regras devem ser validadas nesse momento, essa ação tem relação com necessidade do segundo parametro do tipo BindingResult
    // caso validação identifique algum erro, esse erro estara presente no parametro result
    // no terceiro parametro RedirectAttributes deve utilizar no lugar do modelMap quando deseja enviar um atributo para outra pagina mas a navegação para essa pagina é feita atraves de um redirect não atraves de um forward
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("playlist") Playlist playlist, BindingResult result, RedirectAttributes attr) {
        
    	// verifica se ocorreu erro validação, aqui no caso se o usuario tentou criar uma playlist sem informar um nome por exemplo, caso positivo o usuario vai continuar na pagina de adição de playlist
    	if (result.hasErrors()) {
            return "/playlist/add";
        }
    	// metodo responsavel por salvar a playlist 
        playlistService.salvar(playlist);
        // com objeto attr adicionamos com um atributo mensagem que diz que a playlist foi criada com sucesso 
        attr.addFlashAttribute("mensagem", "Playlist criada com sucesso.");
        return "redirect:/playlists/listar";
    }

    // direcionar o usuario para página que ele fará edição da playlist, como a pagina necessario para fazer edição é igual a pagina de criação de playlist vai utilizar mesmo arquivo add.html
    // o {id} é uma variavel declara ele entre chaves, assim quando a requisiçao enviada para atualização for /playlist/3/atualizar o usuario podera editar os dados da playlist de id = 3
   // para ter acesso a esse valor o @PathVariable passo o id exatamente a string definida no parametro da anotação @GetMapping, com isso o parametro id recebera esse valor no moemento que esse metodo for chamado 
    @GetMapping("/{id}/atualizar")
    public ModelAndView preAtualizar(@PathVariable("id") long id, ModelMap model) {
    	// recupera o id especificado 
        Playlist playlist = playlistService.recuperarPorId(id);
        // para adicionar ao atributo playlist que acabou de recuperar
        model.addAttribute("playlist", playlist);
        // envia esses dados para pagina add.html ao retornar o ModelAndView
        return new ModelAndView("/playlist/add", model);
    }
    
    // verbo @PutMapping para atualizar
    // responsavel por receber os dados editados da playlist e atualiza-los no banco 
    // recebe os mesmos parametros do metodo salvar(), o que muda é o metodo que a service está chamando 
    @PutMapping("/salvar")
    public String atualizar(@Valid @ModelAttribute("playlist") Playlist playlist, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "/playlist/add";
        }

        playlistService.atualizar(playlist);
        attr.addFlashAttribute("mensagem", "Playlist atualizada com sucesso.");
        return "redirect:/playlists/listar";
    }

    @GetMapping("/{id}/remover")
    public String remover(@PathVariable("id") long id, RedirectAttributes attr) {
        playlistService.excluir(id);
        attr.addFlashAttribute("mensagem", "Playlist excluída com sucesso.");
        return "redirect:/playlists/listar";
    }
    
    // o forward é o default do Spring MVC, por que alguns metodos usou redirect e outros deixou padrão, o redirect leva o browser enviar uma nova requisição para o servidor o que faz a URL acessada passa representar esse novo endereço
    // caso fizesse um forward a URL exibida no browser continuaria a mesma assim se usuario fazer um refresh, isto é atualizar a pagina a mesma requisição seria executada levando consigo os mesmo dados da playlist o resultado disso ´
    // é que o usuario mesmo sem querer criaria uma playlist repetida no banco, por isso é comum após criação, atualização e exclusão de algum registro fazer o redirect ao inves de um forward

}
