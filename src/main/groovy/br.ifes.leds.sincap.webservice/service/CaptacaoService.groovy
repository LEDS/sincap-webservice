package br.ifes.leds.sincap.webservice.service

import br.ifes.leds.reuse.utility.Utility
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.CaptacaoDTO
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum.AGUARDANDOCAPTACAO
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.PROBLEMAS_LOGISTICOS
import static br.ifes.leds.sincap.webservice.service.ContextUrls.ASPECTOSLOGISTICOS
import static br.ifes.leds.sincap.webservice.service.ContextUrls.CAPTACAO
import static br.ifes.leds.sincap.webservice.util.CurrentUserUtil.currentUser
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
class CaptacaoService {
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao
    @Autowired
    AplCadastroInterno aplCadastroInterno
    @Autowired
    Utility utility

    @RequestMapping(value = CAPTACAO, method = GET)
    @Secured('ROLE_CAPTADOR')
    def getListNotificacoes(@RequestParam('bancoolhos.id') Long idBancoOlhos) {
        aplProcessoNotificacao.retornarNotificacaoPorEstadoAtualEBancoOlhos AGUARDANDOCAPTACAO, idBancoOlhos
        new ResponseEntity(OK)
    }

    @RequestMapping(value = CAPTACAO, method = POST)
    @Secured('ROLE_CAPTADOR')
    def salvarCaptacao(@ModelAttribute CaptacaoDTO captacao) {
        aplProcessoNotificacao.salvarCaptacao(captacao.id, captacao, currentUser().id)
        new ResponseEntity(CREATED)
    }

    @RequestMapping(value = ASPECTOSLOGISTICOS, method = GET)
    @PreAuthorize('isAuthenticated()')
    def getAspectosLogisticos() {
        aplCadastroInterno.obterCausaNaoDoacao PROBLEMAS_LOGISTICOS
        new ResponseEntity(OK)
    }
}
