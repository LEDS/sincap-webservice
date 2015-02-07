package br.ifes.leds.sincap.webservice.service

import br.ifes.leds.reuse.utility.Utility
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum.AGUARDANDOCAPTACAO
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS
import static br.ifes.leds.sincap.webservice.service.ContextUrls.*
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping(APP_NOTIFICACAO_CAPTACAO)
class IndexService {
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao
    @Autowired
    AplCadastroInterno aplCadastroInterno
    @Autowired
    Utility utility

    @RequestMapping
    def home() {
        ['teste': '1', 'outro': '3']
    }

    @RequestMapping(value = GET_CAPTACOES, method = GET)
    @Secured('ROLE_CAPTADOR')
    def getListNotificacoes() {
        aplProcessoNotificacao.retornarNotificacaoPorEstadoAtualEBancoOlhos AGUARDANDOCAPTACAO, 2L
    }

    @RequestMapping(value = GET_ASPECTOSLOGISTICOS, method = GET)
    @Secured('ROLE_CAPTADOR')
    def getAspectosLogisticos() {
        aplCadastroInterno.obterCausaNaoDoacao PROBLEMAS_ESTRUTURAIS
    }
}
