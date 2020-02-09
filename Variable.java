import java.util.*;
public class Variable implements Comparable<Variable>
{
	String name;
	String type;
	String value;
	public Variable(String nameIn, String typeIn, String valueIn)
	{
		name = nameIn;
		type = typeIn;
		value = valueIn;
	}
	public boolean equals(Variable varIn)
	{
		if(!this.type.equals(varIn.type))
		{
			return false;
		}
		return this.value.equals(varIn.value);
	}
	public int compareTo(Variable varIn)
	{
		if(!type.equals(varIn.type))
		{
			throw new Error();
		}
		if (this.type.equals("num")) {
			return Double.compare(Double.parseDouble(this.value), Double.parseDouble(varIn.value));
		} else {
			return this.value.compareTo(varIn.value);
		}
	}
}
