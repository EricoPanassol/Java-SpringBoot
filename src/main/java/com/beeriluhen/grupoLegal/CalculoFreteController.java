
package com.beeriluhen.grupoLegal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beeriluhen.grupoLegal.util.MontaPesquisaCEP;
import com.beeriluhen.grupoLegal.util.ConsultaViaCEP;
import com.beeriluhen.grupoLegal.util.ResultadoPesquisaCEP;
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;

@RestController
@RequestMapping("/calculoFrete")
public class CalculoFreteController {
    // private Map<String, CustoBasicoTransporte> cidadesAtendidas;
    private IRepositorioDeCidades repositorioDeCidades;

    // public CalculoFreteController() {
    // cidadesAtendidas = new TreeMap<>();
    // // Define o custo do transporte a partir de São Paulo
    // cidadesAtendidas.put("Porto Alegre", new CustoBasicoTransporte("Porto
    // Alegre", 25));
    // cidadesAtendidas.put("Florianópolis", new
    // CustoBasicoTransporte("Florianópolis", 20));
    // cidadesAtendidas.put("Curitiba", new CustoBasicoTransporte("Curitiba", 15));
    // cidadesAtendidas.put("São Paulo", new CustoBasicoTransporte("São Paulo",
    // 10));
    // }

    @Autowired
    public CalculoFreteController(IRepositorioDeCidades repositorioDeCidades) {
        this.repositorioDeCidades = repositorioDeCidades;
    }

    // @GetMapping("/cidadesAtendidas")
    // @CrossOrigin(origins = "*")
    // public ResponseEntity<List<String>> consultaCidadesAtendidas() {
    // List<String> cidades = new LinkedList<>(cidadesAtendidas.keySet());
    // return ResponseEntity
    // .status(HttpStatus.OK)
    // .body(cidades);
    // }

    @GetMapping("/cidadesAtendidas")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> consultaCidadesAtendidas() {
        List<String> cidades = repositorioDeCidades.todas().stream().map(c -> c.getNome()).toList();
        return ResponseEntity.status(HttpStatus.OK).body(cidades);
    }

    // Retorna a cidade correspondente ao CEP ou
    // "invalido" se o CEP for invalido ou
    // "nao atendida" se o CEP é válido mas a cidade não é atendida
    // "excecao" se houve problema na consulta
    private ResultadoPesquisaCEP pesquisaCEP(String umCEP) throws Exception {
        ConsultaViaCEP cvc = new ConsultaViaCEP();
        if (!umCEP.isBlank()) {
            var pesquisa = new MontaPesquisaCEP();
            pesquisa.setCep(umCEP);
            var consulta = pesquisa.consultaPorCEP();
            var resp = cvc.submeteConsultaCEP(consulta);
            return resp;

        } else {
            return null;
        }
    }

    @GetMapping("/validaCEP/{umCEP}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> validaCEP(@PathVariable("umCEP") String umCEP) {
        try {
            var consulta = pesquisaCEP(umCEP);
            if (consulta == null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("invalido");
            }

            // System.out.println(cidade + "======================================" +
            // repositorioDeCidades.todas());

            var nomeCidade = consulta.getLocalidade();
            var cidade = repositorioDeCidades.get(nomeCidade);

            if (cidade != null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(cidade.getNome());
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("nao atendida");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("excecao");
        }

    }

    @PostMapping("/custoEntrega")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustoTempoEntregaDTO> calculaCustoEntrega(@RequestBody final SolicitaCustoDTO solCusto) {
        CustoTempoEntregaDTO custoEntregaInv = new CustoTempoEntregaDTO.Builder().build();
        try {
            var consulta = pesquisaCEP(solCusto.cepDestino());
            if (consulta == null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(custoEntregaInv);
            }
            var nomeCidade = consulta.getLocalidade();
            var cidade = repositorioDeCidades.get(nomeCidade);
            // Se a cidade é atendida, verifica custo e tempo de entrega
            if (cidade != null) {
                CustoTempoEntregaDTO cte = new CustoTempoEntregaDTO.Builder()
                        .cepDestino(solCusto.cepDestino())
                        .cidadeDestino(cidade.getNome())
                        .peso(solCusto.peso())
                        .custoBasico(cidade.getCustoBasico())
                        .custoFinal(cidade.getCustoBasico())
                        .diasUteisParaEntrega(5)
                        .build();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(cte);
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(custoEntregaInv);
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(custoEntregaInv);
        }
    }
}