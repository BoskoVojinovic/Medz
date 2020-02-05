package com.skenons.med.service.generic;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ISSService<R extends JpaRepository<T,K>,T,K>
{
	@Autowired
	protected R repo;
	
	public List<T> getAll()
	{
		return repo.findAll();
	}
	public Optional<T> getOne(K id)
	{
		return repo.findById(id);
	}
	public boolean exists(K id)
	{
		return getOne(id).isPresent();
	}
	public void deleteOne(K id)
	{
		repo.deleteById(id);
	}
	public void saveOne(T object)
	{
		repo.save(object);
	}
	public boolean saveOneById(K id)
	{
		Optional<T> type = repo.findById(id);
		if(type.isPresent())
		{
			saveOne(type.get());
			return true;
		}
		return false;
	}
	public boolean consumeAndSave(K id, Consumer<T> operation)
	{
		Optional<T> type = repo.findById(id);
		if(type.isPresent())
		{
			operation.accept(type.get());
			saveOne(type.get());
			return true;
		}
		return false;
	}
}
