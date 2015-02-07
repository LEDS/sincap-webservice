package br.ifes.leds.sincap.webservice.util

import br.ifes.leds.sincap.controleInterno.cgd.FuncionarioRepository
import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceAdapter implements UserDetailsService {
    @Autowired
    FuncionarioRepository repository

    @Override
    UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        final Funcionario funcionario = repository.findByCpf(cpf)

        if (funcionario == null) {
            throw new UsernameNotFoundException('usuário não encontrado')
        }

        new UserAdapter(funcionario)
    }
}
