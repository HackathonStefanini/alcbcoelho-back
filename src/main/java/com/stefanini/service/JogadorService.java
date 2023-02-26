package com.stefanini.service;

import com.stefanini.entity.Jogador;
import com.stefanini.exceptions.RegraDeNegocioException;
import com.stefanini.repository.JogadorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class JogadorService {

    @Inject
    JogadorRepository jogadorRepository;

    @Transactional
    public void cadastrarJogador(Jogador jogador){
        if (Objects.nonNull(jogador.getId())){
            throw new RuntimeException("Erro ao cadastrar novo jogador");
        }
        if (!jogador.getPassword().isEmpty()) {
            String password = Base64.getEncoder().encodeToString(jogador.getPassword().getBytes());
            jogador.setPassword(password);
        }
        salvar(jogador);
    }

    @Transactional
    public void salvar(Jogador jogador) {
        jogadorRepository.save(jogador);
    }

    public Jogador pegarPorId(Long id) {
        var jogador = jogadorRepository.findById(id);
        if(Objects.isNull(jogador)) {
            throw new RegraDeNegocioException("Ocorreu um erro ao buscar o Jogador de id " + id, Response.Status.NOT_FOUND);
        }
        return jogador;
    }

    @Transactional
    public void alterar(Jogador jogador) {
        jogadorRepository.update(jogador);
    }

    @Transactional
    public void deletar(Long id) {
        jogadorRepository.delete(id);
    }

    public List<Jogador> listarTodos() {
        return jogadorRepository.listAll();
    }
}
