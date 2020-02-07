package com.skenons.med.service.generic;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.Id;

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
	public void deleteAll()
	{
		repo.deleteAll();
	}
	public T saveOne(T object)
	{
		return repo.save(object);
	}
	
	public boolean updateOne(K originalID, T newObject)
	{
		return updateOneUnsafe(originalID, newObject, false);
	}
	
	public boolean updateOneUnsafe(K originalID, T newObject, boolean setNullValues)
	{
		//Imma bend some spacetime now, REFLECTION BITCH!
		Optional<T> type = repo.findById(originalID);
		if(!type.isPresent())
		{
			return false;
		}
		T t = type.get();
		boolean s = true;
		Field f[] = t.getClass().getDeclaredFields();
		for(Field ff : f)
		{
			try
			{
				if(ff.isAnnotationPresent(Id.class))
				{
					continue;
				}
				ff.setAccessible(true);
				if(!setNullValues && (ff.get(newObject)==null || ff.get(newObject).equals("")))//ehh
				{
					continue;
				}
					
				ff.set(t, ff.get(newObject));
			} 
			catch (IllegalArgumentException | IllegalAccessException | SecurityException e)
			{
				e.printStackTrace();
				s = false;
				continue;
			}
		}
		saveOne(t);
		return s;
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
