package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CarroFeignClient carroFeignClient;

	@Autowired
	private MotoFeignClient motoFeignClient;

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);

	}

	public Usuario save(Usuario usuario) {
		Usuario nvUsuario = usuarioRepository.save(usuario);
		return nvUsuario;
	}

	public List<Carro> getCarros(int usuarioId) {
		@SuppressWarnings("unchecked")
		List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
		return carros;
	}

	public List<Moto> getMotos(int usuarioId) {
		@SuppressWarnings("unchecked")
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
		return motos;
	}

	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nvCarro = carroFeignClient.save(carro);
		return nvCarro;

	}

	public Moto saveMoto(int usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nvMoto = motoFeignClient.save(moto);
		return nvMoto;
	}

	public Map<String, Object> getUsuarioAndVehiculos(int usuarioId) {

		Map<String, Object> resultado = new HashMap<String, Object>();
		Usuario nvUsuario = usuarioRepository.findById(usuarioId).orElse(null);
		if (nvUsuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}
		resultado.put("usuario", nvUsuario);
		
		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}else {
			resultado.put("Carros", carros);
		}
		
		List<Moto> motos = motoFeignClient.getCarros(usuarioId);
		if(motos.isEmpty()) {
			resultado.put("Mtoos", "El usuario no tiene motos");
		}else {
			resultado.put("Motos", motos);
		}
		
		return resultado;
		

	}

}
