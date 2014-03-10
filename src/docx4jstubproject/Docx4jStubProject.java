/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */

package docx4jstubproject;


import java.io.File;
import java.util.HashMap;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;


/**
 * There are at least 3 approaches for replacing variables in 
 * a docx.
 * 
 * 1. as shows in this example
 * 2. using Merge Fields (see org.docx4j.model.fields.merge.MailMerger)
 * 3. binding content controls to an XML Part (via XPath)
 * 
 * Approach 3 is the recommended one when using docx4j. See the 
 * ContentControl* examples, Getting Started, and the subforum.
 * 
 * Approach 1, as shown in this example, works in simple cases
 * only.  It won't work if your KEY is split across separate
 * runs in your docx (which often happens), or if you want 
 * to insert images, or multiple rows in a table.
 * 
 * You're encouraged to investigate binding content controls
 * to an XML part.  There is org.docx4j.model.datastorage.migration.FromVariableReplacement
 * to automatically convert your templates to this better
 * approach.
 * 
 * OK, enough preaching.  If you want to use VariableReplace,
 * your variables should be appear like so: ${key1}, ${key2} 
 * 
 * And if you are having problems with your runs being split,
 * VariablePrepare can clean them up.
 *
 */
public class Docx4jStubProject {
	
	public static void main(String[] args) throws Exception {
		
		// Exclude context init from timing
		//org.docx4j.wml.ObjectFactory foo = Context.getWmlObjectFactory();

		// Input docx has variables in it: ${colour}, ${icecream}
		String inputfilepath ="test.docx";

		boolean save = true;
		String outputfilepath = "test2.docx";

		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
                
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("colour", "13/02/14");
		mappings.put("icecream", "1111111");
		
		long start = System.currentTimeMillis();

		
			documentPart.variableReplace(mappings);
		

			
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println("Time: " + total);

		// Save it
		if (save) {
			//SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
			wordMLPackage.save(new File(outputfilepath));
		} else {
			System.out.println(XmlUtils.marshaltoString(documentPart.getJaxbElement(), true,
					true));
		}
	}

}